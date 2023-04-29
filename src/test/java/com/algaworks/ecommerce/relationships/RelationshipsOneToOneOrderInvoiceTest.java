package com.algaworks.ecommerce.relationships;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.sql.Date;

import org.junit.jupiter.api.Test;

import com.algaworks.ecommerce.EntityManagerTest;
import com.algaworks.ecommerce.model.Invoice;
import com.algaworks.ecommerce.model.Order;

class RelationshipsOneToOneOrderInvoiceTest extends EntityManagerTest {
	
	@Test
	void verifyOneToOneRelationshipTest() {
		Order order = entityManager.find(Order.class, 2L);
		
		Invoice invoice = new Invoice();
		invoice.setXml(Invoice.uploadInvoice());
		invoice.setOrder(order);
		invoice.setIssuedatetime(Date.valueOf("2002-05-15"));
		
		entityManager.getTransaction().begin();
		entityManager.persist(invoice);
		entityManager.getTransaction().commit();
		
		entityManager.clear();
		
		Invoice findInvoice = entityManager.find(Invoice.class, order.getId());
		assertNotNull(findInvoice.getId());
		assertNotNull(findInvoice.getXml());
		assertTrue(findInvoice.getXml().length > 0);
		
	}
	
	@Test
	void verifyOneToOneDeleteTest() {
		Order order = entityManager.find(Order.class, 1L);
		
		entityManager.getTransaction().begin();
		entityManager.remove(order);
		entityManager.getTransaction().commit();
		
		Invoice findInvoice = entityManager.find(Invoice.class, order.getId());
		assertNull(findInvoice);
	}
	
}
