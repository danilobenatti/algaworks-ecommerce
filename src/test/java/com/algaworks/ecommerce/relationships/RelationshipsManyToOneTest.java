package com.algaworks.ecommerce.relationships;

import java.time.LocalDateTime;
import java.util.Arrays;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.algaworks.ecommerce.EntityManagerTest;
import com.algaworks.ecommerce.model.Order;
import com.algaworks.ecommerce.model.OrderItem;
import com.algaworks.ecommerce.model.Person;
import com.algaworks.ecommerce.model.Product;
import com.algaworks.ecommerce.model.enums.OrderStatus;

class RelationshipsManyToOneTest extends EntityManagerTest {
	
	@Test
	void verifyManyToOneRelationshipTest() {
		Person c1 = entityManager.find(Person.class, 1L);
		Product p1 = entityManager.find(Product.class, 1L);
		Product p3 = entityManager.find(Product.class, 3L);
		
		Order o1 = new Order();
		o1.setStatus(OrderStatus.WAITING);
		o1.setOrderDate(LocalDateTime.now());
		o1.setPerson(c1);
		
		c1.setOrders(Arrays.asList(o1));
		
		OrderItem oi1 = new OrderItem();
		oi1.setOrder(o1);
		oi1.setProduct(p1);
		oi1.setQuantity(2d);
		oi1.setSubtotal(oi1.calcSubTotal());
		
		OrderItem oi2 = new OrderItem();
		oi2.setOrder(o1);
		oi2.setProduct(p3);
		oi2.setQuantity(2d);
		oi2.setSubtotal(oi2.calcSubTotal());
		
		o1.setOrderitems(Arrays.asList(oi1, oi2));
		
		o1.setTotal(o1.calcTotal());
		
		entityManager.getTransaction().begin();
		entityManager.persist(o1);
		entityManager.persist(oi1);
		entityManager.persist(oi2);
		entityManager.getTransaction().commit();
		
		entityManager.clear();
		
		Order findOrder1 = entityManager.find(Order.class, o1.getId());
		OrderItem findOrderItem1 = entityManager.find(OrderItem.class, oi1.getId());
		OrderItem findOrderItem2 = entityManager.find(OrderItem.class, oi2.getId());
		
		Assertions.assertEquals(o1.getId(), findOrder1.getId());
		Assertions.assertEquals(oi1.getId(), findOrderItem1.getId());
		Assertions.assertEquals(oi2.getId(), findOrderItem2.getId());
		
	}
	
}
