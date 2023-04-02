package com.algaworks.ecommerce.advancedmapping;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.algaworks.ecommerce.EntityManagerTest;
import com.algaworks.ecommerce.model.Order;
import com.algaworks.ecommerce.model.PaymentCreditCard;
import com.algaworks.ecommerce.model.enums.PaymentStatus;

class InheritanceTest extends EntityManagerTest {
	
	@Test
	void IncludePaymentOfOrder() {
		Order order = entityManager.find(Order.class, 1L);
		
		PaymentCreditCard creditCard = new PaymentCreditCard();
		creditCard.setOrder(order);
		creditCard.setNumberOfInstallments(12);
		creditCard.setStatus(PaymentStatus.PROCESSING);
		
		entityManager.getTransaction().begin();
		entityManager.persist(creditCard);
		entityManager.getTransaction().commit();
		
		entityManager.clear();
		
		Order findOrder = entityManager.find(Order.class, order.getId());
		Assertions.assertNotNull(findOrder.getPayment());
		
	}
	
}
