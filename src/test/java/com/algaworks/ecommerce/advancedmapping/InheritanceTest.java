package com.algaworks.ecommerce.advancedmapping;

import java.time.LocalDate;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.algaworks.ecommerce.EntityManagerTest;
import com.algaworks.ecommerce.model.Order;
import com.algaworks.ecommerce.model.PaymentBankSlip;
import com.algaworks.ecommerce.model.enums.PaymentStatus;

class InheritanceTest extends EntityManagerTest {
	
	@Test
	void IncludePaymentOfOrder() {
		Order order = entityManager.find(Order.class, 2L);
		
		PaymentBankSlip bankSlip = new PaymentBankSlip();
		bankSlip.setOrder(order);
		bankSlip.setExpirationDate(LocalDate.now());
		bankSlip.setPayDay(bankSlip.getExpirationDate().minusDays(3));
		bankSlip.setStatus(PaymentStatus.PROCESSING);
		
		order.setPayment(bankSlip);
		
		entityManager.getTransaction().begin();
		entityManager.persist(bankSlip);
		entityManager.getTransaction().commit();
		
		entityManager.clear();
		
		Order findOrder = entityManager.find(Order.class, order.getId());
		Assertions.assertEquals(Byte.valueOf("1"),
			findOrder.getPayment().getStatus().getCode());
		
	}
	
}
