package com.algaworks.ecommerce.jpql;

import static org.junit.jupiter.api.Assertions.assertFalse;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import com.algaworks.ecommerce.EntityManagerTest;
import com.algaworks.ecommerce.model.Product;

import jakarta.persistence.TypedQuery;

class NamedQueryTest extends EntityManagerTest {
	
	@Test
	void executeQuery() {
		TypedQuery<Product> typedQuery = entityManager
			.createNamedQuery("Product.listByCategory", Product.class);
		typedQuery.setParameter("category", 2L);
		
		List<Product> resultList = typedQuery.getResultList();
		
		assertFalse(resultList.isEmpty());
	}
	
	@ParameterizedTest
	@ValueSource(strings = { "Order.list", "Order.listPaid", "Product.listAll",
		"Product.listValue" })
	void executeQueries(String namedQuery) {
		// Order.list -> executeQueryXML
		// Order.listPaid -> executeQueryPaidOrder
		// Product.listValue -> executeQueryExpensiveProducts
		TypedQuery<Object> typedQuery = entityManager
			.createNamedQuery(namedQuery, Object.class);
		
		List<Object> resultList = typedQuery.getResultList();
		
		assertFalse(resultList.isEmpty());
	}
	
}
