package com.algaworks.ecommerce.advancedmapping;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.math.BigDecimal;
import java.time.temporal.ChronoUnit;

import org.junit.jupiter.api.Test;

import com.algaworks.ecommerce.EntityManagerTest;
import com.algaworks.ecommerce.model.Product;
import com.algaworks.ecommerce.model.enums.ProductUnit;

class ColumnsDetailsTest extends EntityManagerTest {
	
	@Test
	void preventInsertingUpdateColumn() {
		Product p = new Product();
		p.setName("Fone de Ouvido K240 AKG");
		p.setDescription("Fone de ouvido hi-fi estéreo profissional");
		p.setUnit(ProductUnit.UNITY);
		p.setUnitPrice(BigDecimal.valueOf(687.77));
		
		entityManager.getTransaction().begin();
		entityManager.persist(p);
		entityManager.getTransaction().commit();
		
		entityManager.clear();
		
		Product findProduct = entityManager.find(Product.class, p.getId());
		assertNotNull(findProduct.getDateCreate());
		assertNull(findProduct.getDateUpdate());
	}
	
	@Test
	void preventUpdatingInsertColumn() {
		Product p = entityManager.find(Product.class, 1L);
		p.setUnitPrice(BigDecimal.valueOf(589.88));
		
		entityManager.getTransaction().begin();
		entityManager.persist(p);
		entityManager.getTransaction().commit();
		
		entityManager.clear();
		
		Product findProduct = entityManager.find(Product.class, p.getId());
		assertEquals(p.getDateCreate().truncatedTo(ChronoUnit.HOURS),
			findProduct.getDateCreate().truncatedTo(ChronoUnit.HOURS));
		assertEquals(p.getDateUpdate().truncatedTo(ChronoUnit.HOURS),
			findProduct.getDateUpdate().truncatedTo(ChronoUnit.HOURS));
	}
}
