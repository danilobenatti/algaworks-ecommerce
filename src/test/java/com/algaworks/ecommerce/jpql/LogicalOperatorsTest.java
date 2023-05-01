package com.algaworks.ecommerce.jpql;

import static org.junit.jupiter.api.Assertions.assertFalse;

import java.util.List;

import org.junit.jupiter.api.Test;

import com.algaworks.ecommerce.EntityManagerTest;
import com.algaworks.ecommerce.model.Order;

import jakarta.persistence.TypedQuery;

class LogicalOperatorsTest extends EntityManagerTest {
	
	@Test
	void usingOperatorsAnd() {
		String jpql = "select o from Order o where o.total > 100 and o.status = 1 and o.person = 1";
		
		TypedQuery<Order> typedQuery = entityManager.createQuery(jpql,
			Order.class);
		List<Order> list = typedQuery.getResultList();
		
		assertFalse(list.isEmpty());
		
	}
	
	@Test
	void usingOperatorsOr() {
		String jpql = "select o from Order o where (o.status = 1 or o.status = 2) and o.total > 100";
		
		TypedQuery<Order> typedQuery = entityManager.createQuery(jpql,
			Order.class);
		List<Order> list = typedQuery.getResultList();
		
		assertFalse(list.isEmpty());
		
	}
	
}
