package com.algaworks.ecommerce.startJpa;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.algaworks.ecommerce.EntityManagerTest;
import com.algaworks.ecommerce.model.Product;

class QueryingRegistryTest extends EntityManagerTest {
	
	@Test
	void searchById() {
		Product product = entityManager.find(Product.class, 1);
//		Product product = entityManager.getReference(Product.class, 1);
		Assertions.assertNotNull(product);
		Assertions.assertEquals("Kindle", product.getName());
	}
	
	@Test
	void updateReference() {
		Product product = entityManager.find(Product.class, 1);
		product.setName("AirPad");
		entityManager.refresh(product);
		Assertions.assertEquals("Kindle", product.getName());
	}
	
}
