package com.algaworks.ecommerce.startJpa;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

import com.algaworks.ecommerce.EntityManagerTest;
import com.algaworks.ecommerce.model.Product;

class QueryingRegistryTest extends EntityManagerTest {
	
	@Test
	void searchById() {
		Product product = entityManager.find(Product.class, 1);
//		Product product = entityManager.getReference(Product.class, 1);
		assertNotNull(product);
		assertEquals("Kindle", product.getName());
	}
	
	@Test
	void updateReference() {
		Product product = entityManager.find(Product.class, 1);
		product.setName("AirPad");
		entityManager.refresh(product);
		assertEquals("Kindle", product.getName());
	}
	
}
