package com.algaworks.ecommerce.advancedmapping;

import java.util.Arrays;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.algaworks.ecommerce.EntityManagerTest;
import com.algaworks.ecommerce.model.Invoice;
import com.algaworks.ecommerce.model.Order;
import com.algaworks.ecommerce.model.OrderItem;
import com.algaworks.ecommerce.model.OrderItemPk;
import com.algaworks.ecommerce.model.Person;
import com.algaworks.ecommerce.model.Product;
import com.algaworks.ecommerce.model.enums.OrderStatus;

class MapsIdTest extends EntityManagerTest {
	
	@Test
	void insertInvoice() {
		Order order = entityManager.find(Order.class, 1L);
		
		Invoice invoice = new Invoice();
		invoice.setOrder(order);
		invoice.setXml("<xml/>");
		
		entityManager.getTransaction().begin();
		entityManager.persist(invoice);
		entityManager.getTransaction().commit();
		
		entityManager.clear();
		
		Invoice findInvoice = entityManager.find(Invoice.class,
				invoice.getId());
		Assertions.assertNotNull(findInvoice);
		Assertions.assertEquals(order.getId(), findInvoice.getId());
	}
	
	@Test
	void insertOrderItem() {
		Person c = entityManager.find(Person.class, 1L);
		Product p1 = entityManager.find(Product.class, 1L);
		
		Order o = new Order();
		o.setPerson(c);
		o.setStatus(OrderStatus.WAITING);
		
		OrderItem oi = new OrderItem();
		oi.setId(new OrderItemPk());
		oi.setOrder(o);
		oi.setProduct(p1);
		oi.setQuantity(1d);
		
		o.setOrderitems(Arrays.asList(oi));
		
		entityManager.getTransaction().begin();
		entityManager.persist(o);
		entityManager.persist(oi);
		entityManager.getTransaction().commit();
		
		entityManager.clear();
		
		OrderItem findItem = entityManager.find(OrderItem.class, oi.getId());
		Assertions.assertEquals(p1.getId(), findItem.getProduct().getId());
		Assertions.assertEquals(o.getId(), findItem.getOrder().getId());
		Assertions.assertEquals(oi.getSubtotal().doubleValue(),
				(findItem.getSubtotal().doubleValue()));
		
	}
	
}
