package com.algaworks.ecommerce.relationships;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

import com.algaworks.ecommerce.EntityManagerTest;
import com.algaworks.ecommerce.model.Order;

class RemovingReferencedEntitiesTest extends EntityManagerTest {
	
	@Test
	void deleteRelatedEntity() {
		
		Order order = entityManager.find(Order.class, 1L);
		
		assertFalse(order.getOrderitems().isEmpty());
		
		entityManager.getTransaction().begin();
//		order.getOrderitems().forEach(i -> entityManager.remove(i));
		entityManager.remove(order);
		entityManager.getTransaction().commit();
		
		entityManager.clear();
		
		Order findOrder = entityManager.find(Order.class, order.getId());
		assertNull(findOrder);
		
	}
	
}
