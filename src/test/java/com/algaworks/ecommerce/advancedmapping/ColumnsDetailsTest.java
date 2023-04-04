package com.algaworks.ecommerce.advancedmapping;

import java.math.BigDecimal;
import java.time.temporal.ChronoUnit;

import org.junit.jupiter.api.Assertions;
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
		Assertions.assertNotNull(findProduct.getDateCreate());
		Assertions.assertNull(findProduct.getDateUpdate());
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
		Assertions.assertEquals(
			p.getDateCreate().truncatedTo(ChronoUnit.SECONDS),
			findProduct.getDateCreate().truncatedTo(ChronoUnit.SECONDS));
		Assertions.assertEquals(
			p.getDateUpdate().truncatedTo(ChronoUnit.MINUTES),
			findProduct.getDateUpdate().truncatedTo(ChronoUnit.MINUTES));
	}
}
