package com.algaworks.ecommerce.jpql;

import static org.junit.jupiter.api.Assertions.assertFalse;

import java.util.List;

import org.junit.jupiter.api.Test;

import com.algaworks.ecommerce.EntityManagerTest;
import com.algaworks.ecommerce.model.Product;

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
	
}
