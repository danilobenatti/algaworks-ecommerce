package com.algaworks.ecommerce.importantdetails;

import java.util.HashMap;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import com.algaworks.ecommerce.EntityManagerTest;
import com.algaworks.ecommerce.model.Order;

import jakarta.persistence.EntityGraph;

class EntityGraphTest extends EntityManagerTest {
	
	@ParameterizedTest
	@ValueSource(strings = { "jakarta.persistence.fetchgraph",
		"jakarta.persistence.loadgraph" })
	void searchEssentialAttributesForOrder1(String property) {
		EntityGraph<Order> entityGraph = entityManager
			.createEntityGraph(Order.class);
		entityGraph.addAttributeNodes("dateCreate", "status", "total", "person",
			"invoice");
		
		HashMap<String, Object> properties = new HashMap<>();
		properties.put(property, entityGraph);
		
		Order order = entityManager.find(Order.class, 1L, properties);
		Assertions.assertNotNull(order);
		
	}
	
	@Test
	void searchEssentialAttributesForOrder2() {
		EntityGraph<Order> entityGraph = entityManager
			.createEntityGraph(Order.class);
		entityGraph.addAttributeNodes("dateCreate", "status", "total", "person",
			"invoice");
		List<Order> list = entityManager
			.createQuery("select o from Order o", Order.class)
			.setHint("jakarta.persistence.fetchgraph", entityGraph)
			.getResultList();
		Assertions.assertFalse(list.isEmpty());
		
	}
}
