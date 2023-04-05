package com.algaworks.ecommerce.relationships;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

import com.algaworks.ecommerce.EntityManagerTest;
import com.algaworks.ecommerce.model.Order;

class OptionalTest extends EntityManagerTest {
	
	@Test
	void verifyBehavior() {
		Order order = entityManager.find(Order.class, 1L);
		
		assertNotNull(order);
	}
}
