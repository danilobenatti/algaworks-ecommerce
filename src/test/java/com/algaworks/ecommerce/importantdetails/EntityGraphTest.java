package com.algaworks.ecommerce.importantdetails;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.text.NumberFormat;
import java.util.HashMap;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import com.algaworks.ecommerce.EntityManagerTest;
import com.algaworks.ecommerce.model.Order;

import jakarta.persistence.EntityGraph;

class EntityGraphTest extends EntityManagerTest {
	
	NumberFormat currency = NumberFormat.getCurrencyInstance();
	
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
		
		Order o = entityManager.find(Order.class, 4L, properties);
		
		logger.info(new StringBuilder().append(o.getId()).append("; ")
			.append(o.getDateCreate()).append("; ")
			.append(o.getStatus().getValue()).append("; ")
			.append(currency.format(o.getTotal())).append("; ").append(
				o.getInvoice() != null ? o.getInvoice().toString() : "N/D"));
		assertNotNull(o);
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
		
		list.forEach(o -> logger.info(new StringBuilder().append(o.getId())
			.append("; ").append(o.getDateCreate()).append("; ")
			.append(o.getStatus().getValue()).append("; ")
			.append(currency.format(o.getTotal())).append("; ").append(
				o.getInvoice() != null ? o.getInvoice().toString() : "N/D")));
		assertFalse(list.isEmpty());
	}
	
}
