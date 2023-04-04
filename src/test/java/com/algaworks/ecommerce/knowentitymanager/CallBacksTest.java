package com.algaworks.ecommerce.knowentitymanager;

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

class CallBacksTest extends EntityManagerTest {
	
	@Test
	void callBacksTest() {
		
		entityManager.getTransaction().begin();
		
		Person person = entityManager.find(Person.class, 1L);
		Product product = entityManager.find(Product.class, 1L);
		
		Order order = new Order();
		order.setPerson(person);
		order.setStatus(OrderStatus.WAITING);
		
		entityManager.persist(order);
		
		entityManager.flush();
		
		OrderItem item = new OrderItem();
		item.setId(new OrderItemPk(order.getId(), product.getId()));
		item.setOrder(order);
		item.setProduct(product);
		item.setQuantity(2d);
		
		order.setOrderitems(Arrays.asList(item));
		
		entityManager.persist(item);
		
		entityManager.flush();
		
		order.setStatus(OrderStatus.PAID);
		
		entityManager.getTransaction().commit();
		
		entityManager.clear();
		
		Order orderVerify = entityManager.find(Order.class, order.getId());
		Assertions.assertNotNull(orderVerify.getDateCreate());
		
	}
	
}
