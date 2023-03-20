package com.algaworks.ecommerce.relationships;

import java.util.Arrays;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.algaworks.ecommerce.EntityManagerTest;
import com.algaworks.ecommerce.model.Order;
import com.algaworks.ecommerce.model.OrderItem;
import com.algaworks.ecommerce.model.OrderItemPk;
import com.algaworks.ecommerce.model.Person;
import com.algaworks.ecommerce.model.Product;
import com.algaworks.ecommerce.model.enums.OrderStatus;

class CheckRelationsShipsOneToManyTest extends EntityManagerTest {
	
	@Test
	void checkRelationsShip() {
		Person client = entityManager.find(Person.class, 1L);
		Product product = entityManager.find(Product.class, 1L);
		
		Order order = new Order();
		order.setStatus(OrderStatus.WAITING);
		order.setPerson(client);
		
		OrderItem item = new OrderItem();
		item.setId(new OrderItemPk());
		item.setQuantity(1d);
		item.setOrder(order);
		item.setProduct(product);
		
		order.setOrderitems(Arrays.asList(item));
		
		entityManager.getTransaction().begin();
		entityManager.persist(order);
		entityManager.persist(item);
		entityManager.getTransaction().commit();
		
		entityManager.clear();
		
		Order findOrder = entityManager.find(Order.class, order.getId());
		Person findClient = entityManager.find(Person.class, client.getId());
		Assertions.assertEquals(client, findOrder.getPerson());
		Assertions.assertFalse(findClient.getOrders().isEmpty());
	}
}
