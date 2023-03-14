package com.algaworks.ecommerce.relationships;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.algaworks.ecommerce.EntityManagerTest;
import com.algaworks.ecommerce.model.Order;
import com.algaworks.ecommerce.model.Person;
import com.algaworks.ecommerce.model.enums.OrderStatus;

class CheckRelationsShipsOneToManyTest extends EntityManagerTest {
	
	@Test
	void checkRelationsShip() {
		Person client = entityManager.find(Person.class, 1L);
		
		Order order = new Order();
		order.setStatus(OrderStatus.WAITING);
		order.setOrderDateInsert(LocalDateTime.now());
		order.setPerson(client);
		
		entityManager.getTransaction().begin();
		entityManager.persist(order);
		entityManager.getTransaction().commit();
		
		entityManager.clear();
		
		Order findOrder = entityManager.find(Order.class, order.getId());
		Person findClient = entityManager.find(Person.class, client.getId());
		Assertions.assertEquals(client, findOrder.getPerson());
		Assertions.assertFalse(findClient.getOrders().isEmpty());
	}
}
