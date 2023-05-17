package com.algaworks.ecommerce.jpql;

import static org.junit.jupiter.api.Assertions.assertFalse;

import java.util.List;

import org.junit.jupiter.api.Test;

import com.algaworks.ecommerce.EntityManagerTest;
import com.algaworks.ecommerce.model.Product;
import com.algaworks.ecommerce.model.Order;

import jakarta.persistence.TypedQuery;

class NamedQueryTest extends EntityManagerTest {
	
	@Test
	void executeQuery1() {
		TypedQuery<Product> typedQuery = entityManager
			.createNamedQuery("Product.listAll", Product.class);
		
		List<Product> resultList = typedQuery.getResultList();
		
		assertFalse(resultList.isEmpty());
	}
	
	@Test
	void executeQuery2() {
		TypedQuery<Product> typedQuery = entityManager
			.createNamedQuery("Product.listByCategory", Product.class);
		typedQuery.setParameter("category", 2L);
		
		List<Product> resultList = typedQuery.getResultList();
		
		assertFalse(resultList.isEmpty());
	}
	
	@Test
	void executeQueryXML() {
		TypedQuery<Order> typedQuery = entityManager
			.createNamedQuery("Order.list", Order.class);
		
		List<Order> resultList = typedQuery.getResultList();
		
		assertFalse(resultList.isEmpty());
	}
	
	@Test
	void executeQueryPaidOrder() {
		TypedQuery<Order> typedQuery = entityManager
			.createNamedQuery("Order.listPaid", Order.class);
		
		List<Order> resultList = typedQuery.getResultList();
		
		assertFalse(resultList.isEmpty());
	}
	
	@Test
	void executeQueryExpensiveProducts() {
		TypedQuery<Order> typedQuery = entityManager
			.createNamedQuery("Product.listValue", Order.class);
		
		List<Order> resultList = typedQuery.getResultList();
		
		assertFalse(resultList.isEmpty());
	}
	
}
