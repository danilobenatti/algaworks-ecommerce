package com.algaworks.ecommerce.eagerlazy;

import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.Test;

import com.algaworks.ecommerce.EntityManagerTest;
import com.algaworks.ecommerce.model.Order;

class CheckEagerLazyTest extends EntityManagerTest {
	
	@Test
	void EagerLazyTest() {
		Order order = entityManager.find(Order.class, 1L);
		
		assertFalse(order.getOrderitems().isEmpty());
	}
}
