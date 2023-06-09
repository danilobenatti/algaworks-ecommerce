package com.algaworks.ecommerce.importantdetails;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.algaworks.ecommerce.EntityManagerTest;
import com.algaworks.ecommerce.model.Order;

import jakarta.persistence.TypedQuery;

class OneToOneLazyTest extends EntityManagerTest {
	
	@Test
	void showProblem() {
		logger.info("Search One Order");
		Order order = entityManager.find(Order.class, 1L);
		Assertions.assertNotNull(order);
		logger.info("Search List of Orders");
		String jpql = "select o from Order o join fetch o.invoice";
		TypedQuery<Order> typedQuery = entityManager.createQuery(jpql,
			Order.class);
		List<Order> list = typedQuery.getResultList();
		Assertions.assertFalse(list.isEmpty());
		
	}
	
}
