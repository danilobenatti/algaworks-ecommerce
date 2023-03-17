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
		
		entityManager.getTransaction().begin();
		
		Person person = entityManager.find(Person.class, 1L);
		Product product1 = entityManager.find(Product.class, 1L);
		Product product3 = entityManager.find(Product.class, 3L);
		
		Order order = new Order();
		order.setPerson(person);
		order.setStatus(OrderStatus.WAITING);
		person.setOrders(Arrays.asList(order));
		
		entityManager.persist(order);
		
		entityManager.flush();
		
		OrderItem item1 = new OrderItem();
		item1.setOrderId(order.getId());
		item1.setProductId(product1.getId());
		item1.setOrder(order);
		item1.setProduct(product1);
		item1.setQuantity(2d);
		
		OrderItem item2 = new OrderItem();
		item2.setOrderId(order.getId());
		item2.setProductId(product3.getId());
		item2.setOrder(order);
		item2.setProduct(product3);
		item2.setQuantity(1d);
		
		order.setOrderitems(Arrays.asList(item1, item2));
		
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
		OrderItem findItem1 = entityManager.find(OrderItem.class,
				new OrderItemPk(2L, 1L));
		Assertions.assertNotNull(findItem1);
		OrderItem findItem2 = entityManager.find(OrderItem.class,
				new OrderItemPk(2L, 3L));
		Assertions.assertNotNull(findItem2);
	}
}
