package com.algaworks.ecommerce.cascadeoperations;

import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

import com.algaworks.ecommerce.EntityManagerTest;
import com.algaworks.ecommerce.model.Order;
import com.algaworks.ecommerce.model.OrderItem;
import com.algaworks.ecommerce.model.OrderItemPk;

class CascadeTypeRemoveTest extends EntityManagerTest {
	
	@Test
	void removeOrderAndItems() {
		Order order = entityManager.find(Order.class, 1L);
		
		entityManager.getTransaction().begin();
//		order.getOrderitems().forEach(i -> entityManager.remove(i));
		entityManager.remove(order); // CascadeType.REMOVE
		entityManager.getTransaction().commit();
		
		entityManager.clear();
		
		Order findOrder = entityManager.find(Order.class, order.getId());
		assertNull(findOrder);
		
	}
	
	@Test
	void removeItemsAndOrder() {
		OrderItem item = entityManager.find(OrderItem.class,
			new OrderItemPk(2L, 3L));
		
		entityManager.getTransaction().begin();
		entityManager.remove(item);
		entityManager.getTransaction().commit();
		
		entityManager.clear();
		
		Order findOrder = entityManager.find(Order.class,
			item.getOrder().getId());
		assertNull(findOrder);
		
	}
	
}
