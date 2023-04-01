package com.algaworks.ecommerce.advancedmapping;

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

class CompositeKeyTest extends EntityManagerTest {
	
	@Test
	void saveOrderItem() {
		
		Person person = entityManager.find(Person.class, 1L);
		Product product1 = entityManager.find(Product.class, 1L);
		Product product3 = entityManager.find(Product.class, 3L);
		
		Order order = new Order();
		order.setPerson(person);
		order.setStatus(OrderStatus.WAITING);
		person.setOrders(Arrays.asList(order));
		
		
		OrderItem item1 = new OrderItem();
		item1.setId(new OrderItemPk());
		item1.setOrder(order);
		item1.setProduct(product1);
		item1.setQuantity(2d);
		
		OrderItem item2 = new OrderItem();
		item2.setId(new OrderItemPk());
		item2.setOrder(order);
		item2.setProduct(product3);
		item2.setQuantity(1d);
		
		order.setOrderitems(Arrays.asList(item1, item2));
		
		entityManager.getTransaction().begin();
		entityManager.persist(order);
		entityManager.persist(item1);
		entityManager.persist(item2);
		entityManager.getTransaction().commit();
		
		entityManager.clear();
		
		Order findOrder = entityManager.find(Order.class, order.getId());
		Assertions.assertNotNull(findOrder);
		Assertions.assertFalse(findOrder.getOrderitems().isEmpty());
	}
	
	@Test
	void searchOrderItem() {
		OrderItem findItem = entityManager.find(OrderItem.class,
				new OrderItemPk(1L, 1L));
		Assertions.assertNotNull(findItem);
	}
}
