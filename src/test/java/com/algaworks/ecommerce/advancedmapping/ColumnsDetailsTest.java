package com.algaworks.ecommerce.advancedmapping;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.algaworks.ecommerce.EntityManagerTest;
import com.algaworks.ecommerce.model.Product;

class ColumnsDetailsTest extends EntityManagerTest {
	
	@Test
	void preventInsertingUpdateColumn() {
		Product p = new Product();
		p.setName("Fone de Ouvido K240 AKG");
		p.setDescription("Fone de ouvido hi-fi estéreo profissional");
		p.setUnitPrice(BigDecimal.valueOf(687.77));
		p.setCreateDate(LocalDateTime.now());
		p.setUpdateDate(LocalDateTime.now());
		
		entityManager.getTransaction().begin();
		entityManager.persist(p);
		entityManager.getTransaction().commit();
		
		entityManager.clear();
		
		Product findProduct = entityManager.find(Product.class, p.getId());
		Assertions.assertNotNull(findProduct.getCreateDate());
		Assertions.assertNull(findProduct.getUpdateDate());
	}
	
	@Test
	void preventUpdatingInsertColumn() {
		Product p = entityManager.find(Product.class, 1L);
		p.setUnitPrice(BigDecimal.valueOf(589.88));
		p.setCreateDate(LocalDateTime.now());
		p.setUpdateDate(LocalDateTime.now());
		
		entityManager.getTransaction().begin();
		entityManager.persist(p);
		entityManager.getTransaction().commit();
		
		entityManager.clear();
		
		Product findProduct = entityManager.find(Product.class, p.getId());
		Assertions.assertNotEquals(
				p.getCreateDate().truncatedTo(ChronoUnit.SECONDS),
				findProduct.getCreateDate().truncatedTo(ChronoUnit.SECONDS));
		Assertions.assertEquals(
				p.getUpdateDate().truncatedTo(ChronoUnit.SECONDS),
				findProduct.getUpdateDate().truncatedTo(ChronoUnit.SECONDS));
	}
}
