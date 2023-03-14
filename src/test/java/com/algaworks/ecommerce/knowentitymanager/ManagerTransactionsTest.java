package com.algaworks.ecommerce.knowentitymanager;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.algaworks.ecommerce.EntityManagerTest;
import com.algaworks.ecommerce.model.Order;
import com.algaworks.ecommerce.model.enums.OrderStatus;

class ManagerTransactionsTest extends EntityManagerTest {
	
	@Test
	void openCloseCancelTransaction() {
		Order order = entityManager.find(Order.class, 1L);
		
		entityManager.getTransaction().begin();
		order.setStatus(OrderStatus.PAID);
		
		if (order.getPayment() != null) {
			entityManager.getTransaction().commit();
		} else {
			entityManager.getTransaction().rollback();
		}
		
		Assertions.assertNotNull(order);
	}
	
}
