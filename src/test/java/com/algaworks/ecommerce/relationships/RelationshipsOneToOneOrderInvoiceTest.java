package com.algaworks.ecommerce.relationships;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.algaworks.ecommerce.EntityManagerTest;
import com.algaworks.ecommerce.model.Invoice;
import com.algaworks.ecommerce.model.Order;

class RelationshipsOneToOneOrderInvoiceTest extends EntityManagerTest {
	
	@Test
	void verifyOneToOneRelationshipTest() {
		Order order = entityManager.find(Order.class, 1L);
		
		Invoice invoice = new Invoice();
		invoice.setXml(Invoice.uploadInvoice());
		invoice.setIssueDate(LocalDateTime.now());
		invoice.setOrder(order);
		
		entityManager.getTransaction().begin();
		entityManager.persist(invoice);
		entityManager.getTransaction().commit();
		
		entityManager.clear();
		
		Invoice findInvoice = entityManager.find(Invoice.class, order.getId());
		Assertions.assertNotNull(findInvoice.getId());
		Assertions.assertNotNull(findInvoice.getXml());
		Assertions.assertTrue(findInvoice.getXml().length > 0);
		
	}
	
	@Test
	void verifyOneToOneDeleteTest() {
		Order order = entityManager.find(Order.class, 1L);
		
		entityManager.getTransaction().begin();
		entityManager.remove(order);
		entityManager.getTransaction().commit();
		
		Invoice findInvoice = entityManager.find(Invoice.class, order.getId());
		Assertions.assertNull(findInvoice);
	}
	
}
