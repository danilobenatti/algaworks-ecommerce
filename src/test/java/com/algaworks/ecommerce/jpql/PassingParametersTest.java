package com.algaworks.ecommerce.jpql;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.algaworks.ecommerce.EntityManagerTest;
import com.algaworks.ecommerce.model.Invoice;
import com.algaworks.ecommerce.model.Order;
import com.algaworks.ecommerce.model.enums.PaymentStatus;

import jakarta.persistence.TemporalType;
import jakarta.persistence.TypedQuery;

class PassingParametersTest extends EntityManagerTest {
	
	@Test
	void passingParameters() {
//		String jpql = "select o from Order o join o.payment pg where o.id = ?1 and pg.status = ?2";
		String jpql = "select o from Order o join o.payment pg where o.id = :orderId and pg.status = :status";
		
		TypedQuery<Order> typedQuery = entityManager.createQuery(jpql,
			Order.class);
//		typedQuery.setParameter(1, 1);
		typedQuery.setParameter("orderId", 1);
//		typedQuery.setParameter(2, PaymentStatus.PROCESSING.getCode());
		typedQuery.setParameter("status", PaymentStatus.PROCESSING.getCode());
		
		List<Order> list = typedQuery.getResultList();
		
		assertEquals(1, list.size());
		
	}
	
	@Test
	void passingParametersDate() {
		String jpql = "select nf from Invoice nf where nf.issuedatetime <= ?1";
		
		TypedQuery<Invoice> typedQuery = entityManager.createQuery(jpql,
			Invoice.class);
		typedQuery.setParameter(1, new Date(), TemporalType.TIMESTAMP);
		
		List<Invoice> list = typedQuery.getResultList();
		
		assertEquals(2, list.size());
		
	}
	
	@Test
	void searchOrdersWithProductId() {
		String jpql = "select o from Order o join o.orderitems i join i.product p where p.id = 1";
		
		TypedQuery<Order> typedQuery = entityManager.createQuery(jpql,
			Order.class);
		List<Order> list = typedQuery.getResultList();
		
		assertNotNull(list);
		
	}
}
