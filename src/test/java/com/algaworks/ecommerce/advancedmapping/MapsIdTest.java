package com.algaworks.ecommerce.advancedmapping;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.sql.Date;
import java.util.Arrays;

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
		Order order = entityManager.find(Order.class, 2L);
		
		Invoice invoice = new Invoice();
		invoice.setOrder(order);
		invoice.setIssuedatetime(Date.valueOf("2002-03-10"));
		invoice.setXml(Invoice.uploadInvoice());
		
		entityManager.getTransaction().begin();
		entityManager.persist(invoice);
		entityManager.getTransaction().commit();
		
		entityManager.clear();
		
		Invoice findInvoice = entityManager.find(Invoice.class,
			invoice.getId());
		assertNotNull(findInvoice);
		assertEquals(order.getId(), findInvoice.getId());
		assertNotNull(findInvoice.getXml());
		assertTrue(findInvoice.getXml().length > 0);
		
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
		assertEquals(p1.getId(), findItem.getProduct().getId());
		assertEquals(o.getId(), findItem.getOrder().getId());
		assertEquals(oi.getSubtotal().doubleValue(),
			(findItem.getSubtotal().doubleValue()));
		
	}
	
}
