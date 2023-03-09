package com.algaworks.ecommerce.relationships;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.algaworks.ecommerce.EntityManagerTest;
import com.algaworks.ecommerce.model.Order;

class RemovingReferencedEntitiesTest extends EntityManagerTest {
	
	@Test
	void deleteRelatedEntity() {
		
		Order order = entityManager.find(Order.class, 1L);
		
		Assertions.assertFalse(order.getOrderitems().isEmpty());
		
		entityManager.getTransaction().begin();
//		order.getOrderitems().forEach(i -> entityManager.remove(i));
		entityManager.remove(order);
		entityManager.getTransaction().commit();
		
		entityManager.clear();
		
		Order findOrder = entityManager.find(Order.class, order.getId());
		Assertions.assertNull(findOrder);
		
	}
	
}
