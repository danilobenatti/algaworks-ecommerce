package com.algaworks.ecommerce.cascadeoperations;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import com.algaworks.ecommerce.EntityManagerTest;
import com.algaworks.ecommerce.model.Order;
import com.algaworks.ecommerce.model.OrderItem;
import com.algaworks.ecommerce.model.OrderItemPk;
import com.algaworks.ecommerce.model.Product;

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
	
	@Test
	void removeProduct() {
		Product product = entityManager.find(Product.class, 1L);
		assertFalse(product.getCategories().isEmpty());
		
		entityManager.getTransaction().begin();
		product.getCategories().clear();
		entityManager.getTransaction().commit();
		
		entityManager.clear();
		
		Product findProduct = entityManager.find(Product.class,
			product.getId());
		assertTrue(findProduct.getCategories().isEmpty());
		
	}
	
}
