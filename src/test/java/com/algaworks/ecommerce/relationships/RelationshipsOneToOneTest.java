package com.algaworks.ecommerce.relationships;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.algaworks.ecommerce.EntityManagerTest;
import com.algaworks.ecommerce.model.Order;
import com.algaworks.ecommerce.model.PaymentCreditCard;
import com.algaworks.ecommerce.model.enums.PaymentStatus;

class RelationshipsOneToOneTest extends EntityManagerTest {
	
	@Test
	void verifyOneToOneRelationshipTest() {
		Order o = entityManager.find(Order.class, 1L);
		PaymentCreditCard pcc = new PaymentCreditCard();
		pcc.setOrder(o);
		pcc.setNumberOfInstallments(3);
		pcc.setStatus(PaymentStatus.PROCESSING);
		
		entityManager.getTransaction().begin();
		entityManager.persist(pcc);
		entityManager.getTransaction().commit();
		
		entityManager.clear();
		
		Order findOrder = entityManager.find(Order.class, o.getId());
		Assertions.assertNotNull(findOrder.getPayment());
		
	}
	
}
