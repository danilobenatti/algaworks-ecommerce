package com.algaworks.ecommerce.jpql;

import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.junit.jupiter.api.Test;

import com.algaworks.ecommerce.EntityManagerTest;
import com.algaworks.ecommerce.model.Order;

import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;

class BasicJPQLTest extends EntityManagerTest {
	
	@Test
	void searchById() {
		/*
		 * JPQL - select o from Order o join o.orderitems i where i.product.unitPrice > 10 
		 * SQL - select o.* from tbl_orders o where o.id = 1
		 */
		String qlString = "select o from Order o where o.id = 1";
		TypedQuery<Order> typedQuery = entityManager.createQuery(qlString,
			Order.class);
		Order order = typedQuery.getSingleResult();
		
		assertNotNull(order);
		assertNotNull(order.getOrderitems());
		
	}
	
	@Test
	void showDifferenceQueries() {
		String jpql = "select o from Order o where o.id = 1";
		TypedQuery<Order> typedQuery = entityManager.createQuery(jpql,
			Order.class);
		Order order1 = typedQuery.getSingleResult();
		
		Query query = entityManager.createQuery(jpql);
		Order order2 = (Order) query.getSingleResult();
		
		List<?> list = query.getResultList();
		
		assertNotNull(order1);
		assertNotNull(order2);
		assertNotNull(list);
		
	}
	
}
