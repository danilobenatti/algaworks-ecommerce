package com.algaworks.ecommerce.knowentitymanager;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.algaworks.ecommerce.EntityManagerTest;
import com.algaworks.ecommerce.model.Order;
import com.algaworks.ecommerce.model.enums.OrderStatus;

class FlushTest extends EntityManagerTest {
	
	@Test
	void callFlush() {
		Exception exception = Assertions.assertThrows(Exception.class, () -> {
			try {
				entityManager.getTransaction().begin();
				
				Order order = entityManager.find(Order.class, 2L);
				order.setStatus(OrderStatus.PAID);
				
				entityManager.flush();
				
				if (order.getPayment() == null) {
					throw new RuntimeException("Order has not been paid.");
				}
				entityManager.getTransaction().commit();
				
			} catch (Exception e) {
				entityManager.getTransaction().rollback();
				throw e;
			}
		}, "RuntimeException was expected");
		Assertions.assertEquals("Order has not been paid.",
				exception.getMessage());
	}
	
}
