package com.algaworks.ecommerce.criteria;

import static org.junit.jupiter.api.Assertions.assertFalse;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

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

class ConditionalExpressionsCriteriaTest extends EntityManagerTest {
	
	@Test
	void usingCondicionalExpressionLike() {
		
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Person> query = builder.createQuery(Person.class);
		Root<Person> root = query.from(Person.class);
		
		query.select(root);
		query.where(builder.like(root.get(Person_.firstname), "%U%"));
		
		TypedQuery<Person> typedQuery = entityManager.createQuery(query);
		List<Person> resultList = typedQuery.getResultList();
		
		resultList.forEach(p -> logger
			.info(String.format("%d - %s", p.getId(), p.getFirstname())));
		assertFalse(resultList.isEmpty());
	}
	
	@ParameterizedTest
	@ValueSource(ints = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11 })
	void usingCondicionalExpression(int i) {
		// 1 -> isNull()
		// 2 -> builder.isNull
		// 3 -> builder.isNotNull
		// 4 -> builder.isEmpty
		// 5 -> builder.isNotEmpty
		// 6 -> builder.greaterThan
		// 7 -> builder.greaterThanOrEqualTo
		// 8 -> builder.lessThan
		// 9 -> builder.lessThanOrEqualTo
		// 10 -> builder.greaterThan && builder.lessThan
		// 11 -> builder.greaterThanOrEqualTo && builder.lessThanOrEqualTo
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Product> query = builder.createQuery(Product.class);
		Root<Product> root = query.from(Product.class);
		
		query.select(root);
		switch (i) {
			case 1 -> query.where(root.get(Product_.image).isNull());
			case 2 -> query.where(builder.isNull(root.get(Product_.image)));
			case 3 -> query.where(builder.isNotNull(root.get(Product_.image)));
			case 4 ->
				query.where(builder.isEmpty(root.get(Product_.categories)));
			case 5 ->
				query.where(builder.isNotEmpty(root.get(Product_.categories)));
			case 6 ->
				query.where(builder.greaterThan(root.get(Product_.unitPrice),
					BigDecimal.valueOf(1000)));
			case 7 -> query.where(builder.greaterThanOrEqualTo(
				root.get(Product_.unitPrice), BigDecimal.valueOf(799.5)));
			case 8 -> query.where(builder.lessThan(root.get(Product_.unitPrice),
				BigDecimal.valueOf(1000)));
			case 9 -> query.where(builder.lessThanOrEqualTo(
				root.get(Product_.unitPrice), BigDecimal.valueOf(799.5)));
			case 10 -> query.where(
				builder.greaterThan(root.get(Product_.unitPrice),
					BigDecimal.valueOf(799.5)),
				builder.lessThan(root.get(Product_.unitPrice),
					BigDecimal.valueOf(3500)));
			case 11 -> query.where(
				builder.greaterThanOrEqualTo(root.get(Product_.unitPrice),
					BigDecimal.valueOf(799.5)),
				builder.lessThanOrEqualTo(root.get(Product_.unitPrice),
					BigDecimal.valueOf(3500)));
			default ->
				throw new IllegalArgumentException("Unexpected value: " + i);
		}
		
		TypedQuery<Product> typedQuery = entityManager.createQuery(query);
		List<Product> resultList = typedQuery.getResultList();
		
		NumberFormat currency = NumberFormat.getCurrencyInstance();
		resultList.forEach(p -> logger.info(String.format("%d - %s - %s",
			p.getId(), p.getName(), currency.format(p.getUnitPrice()))));
		assertFalse(resultList.isEmpty());
	}
	
	@Test
	void usingGreaterLesserConditionalExercise() {
		// All orders paid in the last 3 months
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Order> query = builder.createQuery(Order.class);
		Root<Order> root = query.from(Order.class);
		
		query.select(root);
		query.where(builder.greaterThanOrEqualTo(root.get(Order_.executionDate),
			LocalDateTime.now().minusMonths(3)));
		
		TypedQuery<Order> typedQuery = entityManager.createQuery(query);
		List<Order> resultList = typedQuery.getResultList();
		
		resultList.forEach(o -> logger
			.info(String.format("%d - %s", o.getId(), o.getExecutionDate())));
		
		assertFalse(resultList.isEmpty());
	}
}
