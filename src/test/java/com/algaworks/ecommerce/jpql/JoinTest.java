package com.algaworks.ecommerce.jpql;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import org.junit.jupiter.api.Test;

import com.algaworks.ecommerce.EntityManagerTest;
import com.algaworks.ecommerce.model.Order;

import jakarta.persistence.TypedQuery;

class JoinTest extends EntityManagerTest {
	
	@Test
	void makeJoin() {
		String jpql = "select o, p from Order o join o.payment p where p.status = 1";
		
		TypedQuery<Object[]> typedQuery = entityManager.createQuery(jpql,
			Object[].class);
		List<Object[]> list = typedQuery.getResultList();
		
		assertFalse(list.isEmpty());
		
	}
	
	@Test
	void leftJoin() {
		String jpql = "select o, p from Order o left join o.payment p on p.status = 1";
		
		TypedQuery<Object[]> typedQuery = entityManager.createQuery(jpql, Object[].class);
		List<Object[]> list = typedQuery.getResultList();
		
//		Query query = entityManager.createQuery(jpql, Object[].class);
//		Stream<Object[]> list = query.getResultStream();
		
		assertNotNull(list);
		
	}
	
	@Test
	void useJoinFetch() {
		String jpql = "select o from Order o left join fetch o.payment join fetch o.person left join fetch o.invoice";
		
		TypedQuery<Order> typedQuery = entityManager.createQuery(jpql,
			Order.class);
		List<Order> list = typedQuery.getResultList();
		
		assertFalse(list.isEmpty());
		
	}
	
}
