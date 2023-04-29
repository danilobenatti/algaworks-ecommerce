package com.algaworks.ecommerce.jpql;

import static org.junit.jupiter.api.Assertions.assertFalse;

import java.util.List;

import org.junit.jupiter.api.Test;

import com.algaworks.ecommerce.EntityManagerTest;

import jakarta.persistence.TypedQuery;

class CondicionalExpressionsTest extends EntityManagerTest {
	
	@Test
	void usingCondicionalExpressionLike() {
		String jpql = "select p from Person p where p.firstname like concat('%', :name, '%')";
		
		TypedQuery<Object[]> typedQuery = entityManager.createQuery(jpql,
			Object[].class);
		typedQuery.setParameter("name", "m");
		
		List<Object[]> resultList = typedQuery.getResultList();
		assertFalse(resultList.isEmpty());
		
	}
}
