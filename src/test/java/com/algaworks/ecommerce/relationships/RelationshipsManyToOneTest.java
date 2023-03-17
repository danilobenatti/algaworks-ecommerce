package com.algaworks.ecommerce.relationships;

import java.time.LocalDateTime;
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

class RelationshipsManyToOneTest extends EntityManagerTest {
	
	@Test
	void verifyManyToOneRelationshipTest() {
		
		entityManager.getTransaction().begin();
		
		Person person = entityManager.find(Person.class, 1L);
		Product product1 = entityManager.find(Product.class, 1L);
		Product product3 = entityManager.find(Product.class, 3L);
		
		Order order = new Order();
		order.setStatus(OrderStatus.WAITING);
		order.setOrderDateInsert(LocalDateTime.now());
		order.setPerson(person);
		
		person.setOrders(Arrays.asList(order));
		
		entityManager.persist(order);
		
		entityManager.flush();
		
		OrderItem item1 = new OrderItem();
		item1.setId(new OrderItemPk(order.getId(), product1.getId()));
		item1.setOrder(order);
		item1.setProduct(product1);
		item1.setQuantity(2d);
		
		OrderItem item2 = new OrderItem();
		item2.setId(new OrderItemPk(order.getId(), product3.getId()));
		item2.setOrder(order);
		item2.setProduct(product3);
		item2.setQuantity(2d);
		
		order.setOrderitems(Arrays.asList(item1, item2));
		
		entityManager.persist(item1);
		entityManager.persist(item2);
		
		entityManager.getTransaction().commit();
		
		entityManager.clear();
		
		Order findOrder = entityManager.find(Order.class, order.getId());
		OrderItem findItem1 = entityManager.find(OrderItem.class,
				new OrderItemPk(order.getId(), product1.getId()));
		OrderItem findItem2 = entityManager.find(OrderItem.class,
				new OrderItemPk(order.getId(), product3.getId()));
		
		Assertions.assertEquals(order.getId(), findOrder.getId());
		Assertions.assertEquals(order.getId(), findItem1.getOrder().getId());
		Assertions.assertEquals(order.getId(), findItem2.getOrder().getId());
		
	}
	
}
