package com.algaworks.ecommerce.importantdetails;

import static org.junit.jupiter.api.Assertions.assertFalse;

import java.util.List;

import org.junit.jupiter.api.Test;

import com.algaworks.ecommerce.EntityManagerTest;
import com.algaworks.ecommerce.model.Order;
import com.algaworks.ecommerce.model.Order_;

import jakarta.persistence.EntityGraph;

class ResolveN1Test extends EntityManagerTest {
	
	@Test
	void resolveWithFetch() {
		List<Order> list = entityManager.createQuery(
			"select or from Order or join fetch or.person pe "
				+ "join fetch or.payment pm join fetch or.invoice in",
			Order.class).getResultList();
		assertFalse(list.isEmpty());
	}
	
	@SuppressWarnings("unchecked")
	@Test
	void resolveWithEntityGraph() {
		EntityGraph<Order> entityGraph = entityManager
			.createEntityGraph(Order.class);
		entityGraph.addAttributeNodes(Order_.person, Order_.invoice,
			Order_.payment);
		List<Order> list = entityManager
			.createQuery("select o from Order o", Order.class)
			.setHint("jakarta.persistence.loadgraph", entityGraph)
			.getResultList();
		assertFalse(list.isEmpty());
	}
}
