package com.algaworks.ecommerce.cascadeoperations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Set;

import org.junit.jupiter.api.Test;

import com.algaworks.ecommerce.EntityManagerTest;
import com.algaworks.ecommerce.model.Category;
import com.algaworks.ecommerce.model.Order;
import com.algaworks.ecommerce.model.OrderItem;
import com.algaworks.ecommerce.model.OrderItemPk;
import com.algaworks.ecommerce.model.Person;
import com.algaworks.ecommerce.model.Product;
import com.algaworks.ecommerce.model.enums.OrderStatus;
import com.algaworks.ecommerce.model.enums.ProductUnit;

class CascadeTypeMergeTest extends EntityManagerTest {
	
	@Test
	void updateOrderWithOrderItems() {
		Person person = entityManager.find(Person.class, 1L);
		Product product = entityManager.find(Product.class, 1L);
		
		Order order = new Order();
		order.setId(1L);
		order.setPerson(person);
		order.setStatus(OrderStatus.WAITING);
		order.setExecutionDate(LocalDateTime.now().plusDays(15));
		
		OrderItem item = new OrderItem();
		item.setId(new OrderItemPk());
		item.getId().setOrderId(order.getId());
		item.getId().setProductId(product.getId());
		item.setOrder(order);
		item.setProduct(product);
		item.setQuantity(3d);
		
		order.setOrderitems(Arrays.asList(item));
		
		entityManager.getTransaction().begin();
		entityManager.merge(order);
		entityManager.getTransaction().commit();
		
		entityManager.clear();
		
		Order findOrder = entityManager.find(Order.class, order.getId());
		assertNotNull(findOrder);
		OrderItem findItem = entityManager.find(OrderItem.class, item.getId());
		assertEquals(3d, findOrder.getOrderitems().get(0).getQuantity());
		assertEquals(3d, findItem.getQuantity());
		
	}
	
	@Test
	void updateOrderItemsWithOrder() {
		Person person = entityManager.find(Person.class, 1L);
		Product product = entityManager.find(Product.class, 1L);
		
		Order order = new Order();
		order.setId(1L);
		order.setPerson(person);
		order.setStatus(OrderStatus.PAID);
		order.setExecutionDate(LocalDateTime.now().plusDays(10));
		
		OrderItem item = new OrderItem();
		item.setId(new OrderItemPk());
		item.getId().setOrderId(order.getId());
		item.getId().setProductId(product.getId());
		item.setOrder(order);
		item.setProduct(product);
		item.setQuantity(3d);
		
		order.setOrderitems(Arrays.asList(item));
		
		entityManager.getTransaction().begin();
		entityManager.merge(item);
		entityManager.getTransaction().commit();
		
		entityManager.clear();
		
		Order findOrder = entityManager.find(Order.class, order.getId());
		assertNotNull(findOrder);
		assertEquals(OrderStatus.PAID, findOrder.getStatus());
		OrderItem findItem = entityManager.find(OrderItem.class, item.getId());
		assertEquals(3d, findOrder.getOrderitems().get(0).getQuantity());
		assertEquals(3d, findItem.getQuantity());
		
	}
	
	@Test
	void updateProductWithCategory() {
		Product product = new Product();
		product.setId(1L);
		product.setUnit(ProductUnit.UNITY);
		product.setUnitPrice(BigDecimal.valueOf(699.5));
		product.setName("Kindle Special Edition");
		product.setDescription("The best ever!!");
		
		Category category = entityManager.find(Category.class, 4L);
		category.setName("Tablets");
		
		product.setCategories(Set.of(category));
		
		entityManager.getTransaction().begin();
		entityManager.merge(product);
//		entityManager.persist(product);
		entityManager.getTransaction().commit();
		
		entityManager.clear();
		
		Category findCategory = entityManager.find(Category.class,
			category.getId());
		assertEquals("Tablets", findCategory.getName());
		
	}
}
