package com.algaworks.ecommerce.jpql;

import static org.junit.jupiter.api.Assertions.assertFalse;

import java.util.List;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import com.algaworks.ecommerce.EntityManagerTest;
import com.algaworks.ecommerce.model.Order;

import jakarta.persistence.TypedQuery;

class LogicalOperatorsTest extends EntityManagerTest {
	
	@ParameterizedTest
	@ValueSource(strings = {
		"select o from Order o where o.total > 100 and o.status = 1 and o.person.id = 1",
		"select o from Order o where (o.status = 1 or o.status = 2) and o.total > 100" })
	void usingOperators(String jpql) {
		// usingOperators -> And
		// usingOperators -> Or
		TypedQuery<Order> typedQuery = entityManager.createQuery(jpql,
			Order.class);
		List<Order> list = typedQuery.getResultList();
		
		assertFalse(list.isEmpty());
		
	}
	
}
