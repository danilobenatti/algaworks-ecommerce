package com.algaworks.ecommerce.importantdetails;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.text.NumberFormat;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import com.algaworks.ecommerce.EntityManagerTest;
import com.algaworks.ecommerce.model.Order;
import com.algaworks.ecommerce.model.Order_;
import com.algaworks.ecommerce.model.Person;
import com.algaworks.ecommerce.model.Person_;

import jakarta.persistence.EntityGraph;
import jakarta.persistence.Subgraph;
import jakarta.persistence.TypedQuery;

class EntityGraphTest extends EntityManagerTest {
	
	DateTimeFormatter isoDate = DateTimeFormatter.ISO_DATE;
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
	
	@Test
	void searchEssentialAttributesForOrder3() {
		EntityGraph<Order> entityGraph = entityManager
			.createEntityGraph(Order.class);
		entityGraph.addAttributeNodes("dateCreate", "status", "total");
		
		Subgraph<Person> subgraphPerson = entityGraph.addSubgraph("person",
			Person.class);
		subgraphPerson.addAttributeNodes("firstname", "email", "emails",
			"phones");
		
		TypedQuery<Order> typedQuery = entityManager
			.createQuery("select o from Order o", Order.class);
		typedQuery.setHint("jakarta.persistence.fetchgraph", entityGraph);
		
		List<Order> list = typedQuery.getResultList();
		
		list.forEach(o -> logger.info(new StringBuilder().append(o.getId())
			.append("; ").append(o.getDateCreate()).append("; ")
			.append(o.getStatus().getValue()).append("; ")
			.append(currency.format(o.getTotal()))));
		assertFalse(list.isEmpty());
	}
	
	@SuppressWarnings("unchecked")
	@Test
	void searchEssentialAttributesForOrder4() {
		EntityGraph<Order> entityGraph = entityManager
			.createEntityGraph(Order.class);
		entityGraph.addAttributeNodes(Order_.executionDate, Order_.status,
			Order_.total);
		
		Subgraph<Person> subgraphPerson = entityGraph
			.addSubgraph(Order_.person);
		subgraphPerson.addAttributeNodes(Person_.firstname, Person_.email,
			Person_.emails, Person_.phones);
		
		TypedQuery<Order> typedQuery = entityManager
			.createQuery("select o from Order o", Order.class);
		typedQuery.setHint("jakarta.persistence.fetchgraph", entityGraph);
		
		List<Order> list = typedQuery.getResultList();
		
		list.forEach(o -> logger.info(new StringBuilder().append(o.getId())
			.append("; ").append(o.getDateCreate().format(isoDate)).append("; ")
			.append(o.getStatus().getValue()).append("; ")
			.append(currency.format(o.getTotal())).append("; ")
			.append(o.getExecutionDate() != null
				? o.getExecutionDate().format(isoDate)
				: "N/D")));
		assertFalse(list.isEmpty());
	}
	
	@Test
	void searchEssentialAttributesForOrderWithNamedEntityGraph() {
		EntityGraph<?> entityGraph = entityManager
			.createEntityGraph("Order.essentialData");
		entityGraph.addAttributeNodes("payment");
//		entityGraph.addSubgraph("payment").addAttributeNodes("status");
		
		TypedQuery<Order> typedQuery = entityManager
			.createQuery("select o from Order o", Order.class);
		typedQuery.setHint("jakarta.persistence.fetchgraph", entityGraph);
		
		List<Order> list = typedQuery.getResultList();
		
		list.forEach(o -> logger.info(new StringBuilder().append(o.getId())
			.append("; ").append(o.getDateCreate().format(isoDate)).append("; ")
			.append(o.getStatus().getValue()).append("; ")
			.append(currency.format(o.getTotal())).append("; ")
			.append(o.getExecutionDate() != null
				? o.getExecutionDate().format(isoDate)
				: "N/D")));
		assertFalse(list.isEmpty());
	}
}
