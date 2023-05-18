package com.algaworks.ecommerce.criteria;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

import com.algaworks.ecommerce.EntityManagerTest;
import com.algaworks.ecommerce.model.Order;

import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

class BasicCriteriaTest extends EntityManagerTest {
	
	@Test
	void searchById() {
		
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Order> query = builder.createQuery(Order.class);
		Root<Order> root = query.from(Order.class);
		
		query.select(root); // it is implied
		
		query.where(builder.equal(root.get("id"), 1));
		
		// String jpql = "select o from Order o where o.id = 1";
		
		TypedQuery<Order> typedQuery = entityManager.createQuery(query);
		Order order = typedQuery.getSingleResult();
		
		assertNotNull(order);
		assertNotNull(order.getOrderitems());
		
	}
	
}
