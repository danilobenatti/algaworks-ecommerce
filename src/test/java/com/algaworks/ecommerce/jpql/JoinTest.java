package com.algaworks.ecommerce.jpql;

import static org.junit.jupiter.api.Assertions.assertFalse;

import java.util.List;

import org.junit.jupiter.api.Test;

import com.algaworks.ecommerce.EntityManagerTest;

import jakarta.persistence.TypedQuery;

class JoinTest extends EntityManagerTest {
	
	@Test
	void makeJoin() {
		String jpql = "select o, p from Order o inner join o.payment p where p.status = 1";
		
		TypedQuery<Object[]> typedQuery = entityManager.createQuery(jpql,
			Object[].class);
		List<Object[]> list = typedQuery.getResultList();
		
		assertFalse(list.isEmpty());
		
	}
	
	@Test
	void leftJoin() {
		String jpql = "select o, p from Order o left join o.payment p on p.status = 1";
		
		TypedQuery<Object[]> typedQuery = entityManager.createQuery(jpql,
			Object[].class);
		List<Object[]> list = typedQuery.getResultList();
		
		assertFalse(list.isEmpty());
		
	}
	
}
