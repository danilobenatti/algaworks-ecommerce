package com.algaworks.ecommerce.jpql;

import static org.junit.jupiter.api.Assertions.assertFalse;

import java.util.List;

import org.junit.jupiter.api.Test;

import com.algaworks.ecommerce.EntityManagerTest;
import com.algaworks.ecommerce.model.Order;

import jakarta.persistence.TypedQuery;

class PathExpressionTest extends EntityManagerTest {
	
	@Test
	void usePathExpressions() {
		String jpql = "select o.person.firstname from Order o";
		
		TypedQuery<Object[]> typedQuery = entityManager.createQuery(jpql,
			Object[].class);
		List<Object[]> list = typedQuery.getResultList();
		
		assertFalse(list.isEmpty());
		
	}
	
	@Test
	void usePathExpressionsReview() {
		String jpql = "select o from Order o inner join o.person p where p.firstname = 'Luiz Fernando'";
		
		TypedQuery<Order> typedQuery = entityManager.createQuery(jpql,
			Order.class);
		List<Order> list = typedQuery.getResultList();
		
		assertFalse(list.isEmpty());
		
	}
	
	@Test
	void searchForOrderWithSpecificProduct() {
		String jpql = "select o from Order o join o.orderitems i where i.id.productId = 1";
//		String jpql = "select o from Order o join o.orderitems i where i.product.id = 1";
//		String jpql = "select o from Order o join o.orderitems i join i.product p where p.id = 1";
		
		TypedQuery<Order> typedQuery = entityManager.createQuery(jpql,
			Order.class);
		List<Order> list = typedQuery.getResultList();
		
		assertFalse(list.isEmpty());
		
	}
	
}
