package com.algaworks.ecommerce.criteria;

import static com.algaworks.ecommerce.util.DatesFunctions.getAge;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.algaworks.ecommerce.EntityManagerTest;
import com.algaworks.ecommerce.model.Order;
import com.algaworks.ecommerce.model.Order_;
import com.algaworks.ecommerce.model.Person;
import com.algaworks.ecommerce.model.Person_;
import com.algaworks.ecommerce.model.Product;
import com.algaworks.ecommerce.model.Product_;

import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.criteria.Subquery;

class SubQueriesCriteriaTest extends EntityManagerTest {
	
	@Test
	void searchSubQueries() {
		// Most expensive products
		/**
		 * JPQL = select p from Product p where p.unitPrice = (select
		 * max(unitPrice) from Product)
		 */
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Product> criteriaQuery = criteriaBuilder
			.createQuery(Product.class);
		Root<Product> root = criteriaQuery.from(Product.class);
		
		criteriaQuery.select(root);
		
		Subquery<BigDecimal> subquery = criteriaQuery
			.subquery(BigDecimal.class);
		Root<Product> rootSubquery = subquery.from(Product.class);
		subquery
			.select(criteriaBuilder.max(rootSubquery.get(Product_.unitPrice)));
		
		criteriaQuery.where(
			criteriaBuilder.equal(root.get(Product_.unitPrice), subquery));
		
		TypedQuery<Product> typedQuery = entityManager
			.createQuery(criteriaQuery);
		List<Product> list = typedQuery.getResultList();
		
		NumberFormat currency = NumberFormat.getCurrencyInstance();
		list.forEach(p -> logger.info(String.format("Product Id: %d; price: %s",
			p.getId(), currency.format(p.getUnitPrice()))));
		
		assertFalse(list.isEmpty());
	}
	
	@Test
	void searchSubQueries2() {
		// All orders above sales average
		/*
		 * JPQL = select o from Order o where o.total > (select avg(total) from
		 * Order)
		 */
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Order> criteriaQuery = criteriaBuilder
			.createQuery(Order.class);
		Root<Order> root = criteriaQuery.from(Order.class);
		
		criteriaQuery.select(root);
		
		Subquery<BigDecimal> subquery = criteriaQuery
			.subquery(BigDecimal.class);
		Root<Order> rootSubquery = subquery.from(Order.class);
		subquery.select(criteriaBuilder.avg(rootSubquery.get(Order_.total))
			.as(BigDecimal.class));
		
		criteriaQuery.where(
			criteriaBuilder.greaterThan(root.get(Order_.total), subquery));
		
		TypedQuery<Order> typedQuery = entityManager.createQuery(criteriaQuery);
		List<Order> list = typedQuery.getResultList();
		
		NumberFormat currency = NumberFormat.getCurrencyInstance();
		list.forEach(o -> logger.info(String.format("Order Id: %d; total: %s",
			o.getId(), currency.format(o.getTotal()))));
		
		assertFalse(list.isEmpty());
	}
	
	@Test
	void searchSubQueries3() {
		// Good customers(person)
		/*
		 * JPQL = select p from Person p where 600 < (select sum(o.total) from
		 * Order o where o.person = p)
		 */
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Object[]> criteriaQuery = criteriaBuilder
			.createQuery(Object[].class);
		Root<Person> root = criteriaQuery.from(Person.class);
		
		criteriaQuery.multiselect(root, criteriaBuilder.function(
			"calc_total_by_person", Double.class, root.get(Person_.id)));
		
		Subquery<BigDecimal> subquery = criteriaQuery
			.subquery(BigDecimal.class);
		Root<Order> subqueryRoot = subquery.from(Order.class);
		subquery.select(criteriaBuilder.sum(subqueryRoot.get(Order_.total)));
//		subquery.where(criteriaBuilder.equal(root.get(Person_.id),
//			subqueryRoot.get(Order_.person).get(Person_.id)));
		subquery.where(
			criteriaBuilder.equal(root, subqueryRoot.get(Order_.person)));
		
		criteriaQuery.where(
			criteriaBuilder.greaterThan(subquery, BigDecimal.valueOf(600)));
		
		TypedQuery<Object[]> typedQuery = entityManager
			.createQuery(criteriaQuery);
		List<Object[]> list = typedQuery.getResultList();
		
		NumberFormat currency = NumberFormat.getCurrencyInstance();
		list.forEach(p -> logger
			.info(String.format("Person Id: %d; Name: %s; Age: %s; Total: %s",
				((Person) p[0]).getId(), ((Person) p[0]).getFirstname(),
				getAge(((Person) p[0]).getBirthday()), currency.format(p[1]))));
		
		assertFalse(list.isEmpty());
	}
	
}
