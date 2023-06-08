package com.algaworks.ecommerce.cascadeoperations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Set;

import org.junit.jupiter.api.Test;

import com.algaworks.ecommerce.EntityManagerTest;
import com.algaworks.ecommerce.model.Category;
import com.algaworks.ecommerce.model.Order;
import com.algaworks.ecommerce.model.OrderItem;
import com.algaworks.ecommerce.model.OrderItemPk;
import com.algaworks.ecommerce.model.Person;
import com.algaworks.ecommerce.model.Product;
import com.algaworks.ecommerce.model.enums.Gender;
import com.algaworks.ecommerce.model.enums.OrderStatus;
import com.algaworks.ecommerce.model.enums.ProductUnit;

class CascadeTypePersistTest extends EntityManagerTest {
	
	@Test
	void persistOrderWithItems() {
		Person person = entityManager.find(Person.class, 1L);
		Product product = entityManager.find(Product.class, 1L);
		
		Order order = new Order();
		order.setPerson(person);
		order.setStatus(OrderStatus.WAITING);
		
		OrderItem item = new OrderItem();
		item.setId(new OrderItemPk());
		item.setOrder(order);
		item.setProduct(product);
		item.setQuantity(1d);
		item.setOrder(order);
		
		order.setOrderitems(Arrays.asList(item));
		
		entityManager.getTransaction().begin();
		entityManager.persist(order);
		entityManager.getTransaction().commit();
		
		entityManager.clear();
		
		Order findOrder = entityManager.find(Order.class, order.getId());
		assertNotNull(findOrder);
		assertFalse(findOrder.getOrderitems().isEmpty());
		
	}
	
	@Test
	void persistOrderItemsWithOrder() {
		Person person = entityManager.find(Person.class, 1L);
		Product product = entityManager.find(Product.class, 1L);
		
		Order order = new Order();
		order.setPerson(person);
		order.setStatus(OrderStatus.WAITING);
		
		OrderItem item = new OrderItem();
		item.setId(new OrderItemPk());
		item.setOrder(order);
		item.setProduct(product);
		item.setQuantity(2d);
		item.setOrder(order);
		
		order.setOrderitems(Arrays.asList(item));
		
		entityManager.getTransaction().begin();
		entityManager.persist(item);
		entityManager.getTransaction().commit();
		
		entityManager.clear();
		
		OrderItem findItem = entityManager.find(OrderItem.class, item.getId());
		assertNotNull(findItem);
		assertEquals(order.getId(), findItem.getOrder().getId());
		
	}
	
	@Test
	void persistOrderWithPerson() {
		Person person = new Person();
		person.setFirstname("José Carlos");
		person.setBirthday(LocalDate.of(1987, Month.FEBRUARY, 15));
		person.setGender(Gender.MALE);
		person.setTaxIdNumber("035.194.420-60");
		HashMap<Character, String> phones = new HashMap<Character, String>();
		phones.put('H', "7788889999");
		person.setPhones(phones);
		person.setEmail("jose@mail.org");
		
		Order order = new Order();
		order.setPerson(person);
		order.setStatus(OrderStatus.WAITING);
		
		entityManager.getTransaction().begin();
		entityManager.persist(order);
		entityManager.getTransaction().commit();
		
		entityManager.clear();
		
		Person findPerson = entityManager.find(Person.class, person.getId());
		assertNotNull(findPerson);
		
	}
	
	@Test
	void persistProductWithCategory() {
		Product product = new Product();
		product.setName("Fone de Ouvido");
		product.setDescription("A melhor qualidade de som");
		product.setUnitPrice(BigDecimal.valueOf(250.00));
		product.setUnit(ProductUnit.UNITY);
		
		Category category = new Category();
		category.setName("Áudio e Vídeo");
		category.setParentCategory(entityManager.find(Category.class, 1L));
		
		product.setCategories(Set.of(category, category.getParentCategory()));
		
		entityManager.getTransaction().begin();
		entityManager.persist(product);
		entityManager.getTransaction().commit();
		
		entityManager.clear();
		
		Category findCategory = entityManager.find(Category.class,
			category.getId());
		assertNotNull(findCategory);
		
		Product findProduct = entityManager.find(Product.class,
			product.getId());
		assertEquals(1, findProduct.getCategories().stream()
			.filter(i -> i.getName().equals("Áudio e Vídeo")).count());
		
	}
}
