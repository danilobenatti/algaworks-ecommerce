package com.algaworks.ecommerce.eagerlazy;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.algaworks.ecommerce.EntityManagerTest;
import com.algaworks.ecommerce.model.Order;

class CheckEagerLazyTest extends EntityManagerTest {
	
	@Test
	void EagerLazyTest() {
		Order order = entityManager.find(Order.class, 1L);
		
		Assertions.assertFalse(order.getOrderitems().isEmpty());
	}
}
