package com.algaworks.ecommerce.jpql;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.junit.jupiter.api.Test;

import com.algaworks.ecommerce.EntityManagerTest;
import com.algaworks.ecommerce.dto.ProductDTO;
import com.algaworks.ecommerce.model.Order;
import com.algaworks.ecommerce.model.Person;

import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;

class BasicJPQLTest extends EntityManagerTest {
	
	static Logger logger = Logger.getLogger(BasicJPQLTest.class.getName());
	
	@Test
	void searchById() {
		/*
		 * JPQL - select o from Order o join o.orderitems i where
		 * i.product.unitPrice > 10 SQL - select o.* from tbl_orders o where
		 * o.id = 1
		 */
		String qlString = "select o from Order o where o.id = 2";
		TypedQuery<Order> typedQuery = entityManager.createQuery(qlString,
			Order.class);
		Order order = typedQuery.getSingleResult();
		
		assertNotNull(order);
		assertNotNull(order.getOrderitems());
		
	}
	
	@Test
	void showDifferenceQueries() {
		String jpql = "select o from Order o where o.id = 2";
		TypedQuery<Order> typedQuery = entityManager.createQuery(jpql,
			Order.class);
		Order order1 = typedQuery.getSingleResult();
		
		Query query = entityManager.createQuery(jpql);
		Order order2 = (Order) query.getSingleResult();
		
		List<?> list = query.getResultList();
		
		assertNotNull(order1);
		assertNotNull(order2);
		assertNotNull(list);
		
	}
	
	@Test
	void selectAttributeToReturn() {
		String jpql = "select p.name from Product p";
		TypedQuery<String> typedQuery = entityManager.createQuery(jpql,
			String.class);
		List<String> list = typedQuery.getResultList();
		assertEquals(String.class, list.get(0).getClass());
		
		String jpql2 = "select o.person from Order o";
		TypedQuery<Person> typedQuery2 = entityManager.createQuery(jpql2,
			Person.class);
		List<Person> list2 = typedQuery2.getResultList();
		assertEquals(Person.class, list2.get(0).getClass());
		
	}
	
	@Test
	void resultProjection() {
		String jpql = "select id, name from Product";
		
		TypedQuery<Object[]> typedQuery = entityManager.createQuery(jpql,
			Object[].class);
		List<Object[]> list = typedQuery.getResultList();
		
		assertEquals(2, list.get(0).length);
		
		list.forEach(i -> logger.log(Level.INFO, i[0] + ", " + i[1]));
		
	}
	
	@Test
	void resultProjectionFromDTO() {
		String jpql = "select new com.algaworks.ecommerce.dto.ProductDTO(id, name) from Product";
		
		TypedQuery<ProductDTO> typedQuery = entityManager.createQuery(jpql,
			ProductDTO.class);
		List<ProductDTO> list = typedQuery.getResultList();
		
		assertFalse(list.isEmpty());
		
		list.forEach(
			i -> logger.log(Level.INFO, i.getId() + ", " + i.getName()));
		
	}
	
	@Test
	void sortResults() {
		String jpql = "select p from Person p order by p.birthday desc";
		
		TypedQuery<Person> typedQuery = entityManager.createQuery(jpql,
			Person.class);
		List<Person> list = typedQuery.getResultList();
		
		assertFalse(list.isEmpty());
		
		list.forEach(
			p -> logger.log(Level.INFO, p.getId() + "; " + p.getFirstname()));
		
	}
	
	@Test
	void usingDistinct() {
		String jpql = "select distinct o from Order o join o.orderitems i "
			+ "join i.product p where p.id in (1, 3, 4, 5, 6)";
		
		TypedQuery<Order> typedQuery = entityManager.createQuery(jpql,
			Order.class);
		List<Order> list = typedQuery.getResultList();
		
		assertFalse(list.isEmpty());
		
		logger.log(Level.INFO, "{0}", list.size());
		
		list.forEach(o -> logger.log(Level.INFO, "{0}", String.format(
			"Order: %d, Total: %.2f", o.getId(), o.getTotal().floatValue())));
		
	}
	
}
