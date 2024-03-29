package com.algaworks.ecommerce.knowentitymanager;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

import com.algaworks.ecommerce.EntityManagerTest;
import com.algaworks.ecommerce.model.Order;
import com.algaworks.ecommerce.model.Person;
import com.algaworks.ecommerce.model.Product;
import com.algaworks.ecommerce.model.enums.OrderStatus;

class ListenersTest extends EntityManagerTest {
	
	@Test
	void loadEntitiesAndShowLogs() {
		Product product = entityManager.find(Product.class, 1L);
		Order order = entityManager.find(Order.class, 1L);
		assertInstanceOf(Product.class, product);
		assertInstanceOf(Order.class, order);
	}
	
	@Test
	void listenersTest() {
		Person person = entityManager.find(Person.class, 1L);
		Order order = new Order();
		order.setPerson(person);
		order.setStatus(OrderStatus.WAITING);
		
		entityManager.getTransaction().begin();
		entityManager.persist(order);
		
		entityManager.flush();
		
		order.setStatus(OrderStatus.PAID);
		entityManager.getTransaction().commit();
		
		entityManager.clear();
		
		Order orderVerify = entityManager.find(Order.class, order.getId());
		assertNotNull(orderVerify.getDateCreate());
		
	}
	
}
