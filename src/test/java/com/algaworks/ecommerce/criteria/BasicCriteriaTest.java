package com.algaworks.ecommerce.criteria;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;

import com.algaworks.ecommerce.EntityManagerTest;
import com.algaworks.ecommerce.model.Order;
import com.algaworks.ecommerce.model.Person;

import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

class BasicCriteriaTest extends EntityManagerTest {
	
	@Test
	void searchById() {
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Order> query = builder.createQuery(Order.class);
		Root<Order> root = query.from(Order.class);
		
		query.select(root); // it is implied
		query.where(builder.equal(root.get("id"), 1L));
		
		// String jpql = "select o from Order o where o.id = 1";
		
		TypedQuery<Order> typedQuery = entityManager.createQuery(query);
		Order order = typedQuery.getSingleResult();
		
		assertNotNull(order);
	}
	
	@Test
	void selectPersonFromOrder() {
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Person> query = builder.createQuery(Person.class);
		Root<Order> root = query.from(Order.class);
		
		query.select(root.get("person"));
		query.where(builder.equal(root.get("id"), 1L));
		
		// String jpql = "select o.person from Order o where o.id = 1";
		
		TypedQuery<Person> typedQuery = entityManager.createQuery(query);
		Person person = typedQuery.getSingleResult();
		
		assertEquals("Luiz Fernando", person.getFirstname());
	}
	
	@Test
	void selectTotalFromOrder() {
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<BigDecimal> query = builder.createQuery(BigDecimal.class);
		Root<Order> root = query.from(Order.class);
		
		query.select(root.get("total"));
		query.where(builder.equal(root.get("id"), 1L));
		
		// String jpql = "select o.total from Order o where o.id = 1";
		
		TypedQuery<BigDecimal> typedQuery = entityManager.createQuery(query);
		BigDecimal total = typedQuery.getSingleResult();
		
		assertEquals(new BigDecimal("505.00"), total);
	}
	
}
