package com.algaworks.ecommerce.jpql;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.io.File;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import com.algaworks.ecommerce.EntityManagerTest;
import com.algaworks.ecommerce.model.Product;
import com.algaworks.ecommerce.util.UpLoadFiles;

import jakarta.persistence.Query;

class OperationBatchTest extends EntityManagerTest {
	
	private static Logger logger = Logger
		.getLogger(OperationBatchTest.class.toString());
	
	private static int LIMIT_OF_INSERTIONS = 10;
	
	@Test
	void batchInsert() {
		File file = new File(getClass().getClassLoader()
			.getResource("files/import_products.txt").getFile());
		List<Product> listNewProducts = UpLoadFiles.getProductsFromFile(file);
		
		entityManager.getTransaction().begin();
		if (!listNewProducts.isEmpty()) {
			int i = 0;
			for (Product product : listNewProducts) {
				entityManager.persist(product);
				++i;
				if (i == LIMIT_OF_INSERTIONS) {
					entityManager.flush();
					entityManager.clear();
					break;
				}
			}
		}
		entityManager.getTransaction().commit();
		
		List<Long> ids = listNewProducts.stream().map(p -> p.getId()).toList();
		Product p1 = entityManager.find(Product.class, ids.get(0));
		Product p2 = entityManager.find(Product.class, ids.get(9));
		
		NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(
			new Locale.Builder().setLanguage("pt").setRegion("BR").build());
		
		logger.log(Level.INFO, "{0}", String.format("%d - %s, %s", p1.getId(),
			p1.getName(), currencyFormat.format(p1.getUnitPrice())));
		logger.log(Level.INFO, "{0}", String.format("%d - %s, %s", p2.getId(),
			p2.getName(), currencyFormat.format(p2.getUnitPrice())));
		
		assertNotNull(p1);
		assertNotNull(p2);
	}
	
	@ParameterizedTest
	@ValueSource(strings = {
		"update Product p set p.unitPrice = p.unitPrice * :value "
			+ "where p.id between 1 and 4",
		"update Product p1 set p1.unitPrice = p1.unitPrice * :value "
			+ "where exists (select 1 from p1.categories c2 where c2.id = 4)",
		"update Product p set p.unitPrice = p.unitPrice * :value "
			+ "where datediff(now(), p.dateCreate) >= 25" })
	void batchUpdate(String jpql) {
		Query query = entityManager.createQuery(jpql);
		query.setParameter("value", BigDecimal.valueOf(0.95)); // 0.95 - 5% off
		
		entityManager.getTransaction().begin();
		query.executeUpdate();
		entityManager.getTransaction().commit();
		
		Product p = entityManager.find(Product.class, 1L);
		assertNotEquals(BigDecimal.valueOf(799.50), p.getUnitPrice());
	}
	
	@Test
	void bachRemove() {
		String jpql = "delete from Product p where p.id between 7 and 16";
		
		entityManager.getTransaction().begin();
		
		Query query = entityManager.createQuery(jpql);
		query.executeUpdate();
		
		entityManager.getTransaction().commit();
		
		for (int i = 7; i <= 16; i++) {
			assertNull(entityManager.find(Product.class, i));
		}
	}
	
}
