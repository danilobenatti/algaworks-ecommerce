package com.algaworks.ecommerce.criteria;

import static org.junit.jupiter.api.Assertions.assertFalse;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import com.algaworks.ecommerce.EntityManagerTest;
import com.algaworks.ecommerce.model.Order;
import com.algaworks.ecommerce.model.Order_;
import com.algaworks.ecommerce.model.Payment;
import com.algaworks.ecommerce.model.Payment_;
import com.algaworks.ecommerce.model.Person;
import com.algaworks.ecommerce.model.Person_;
import com.algaworks.ecommerce.model.Product;
import com.algaworks.ecommerce.model.Product_;
import com.algaworks.ecommerce.model.enums.OrderStatus;
import com.algaworks.ecommerce.model.enums.PaymentStatus;

import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
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
	
	@Test
	void usingBetween() {
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Order> query = builder.createQuery(Order.class);
		Root<Order> root = query.from(Order.class);
		
		query.select(root);
		
		query.where(
			builder.between(root.get(Order_.total).as(Double.class), 499.5,
				3500.0), // and
			builder.between(root.get(Order_.executionDate),
				LocalDateTime.now().minusYears(1), LocalDateTime.now()));
		
		TypedQuery<Order> typedQuery = entityManager.createQuery(query);
		List<Order> resultList = typedQuery.getResultList();
		
		resultList.forEach(
			o -> logger.info(String.format("Order: %d - Total: %s", o.getId(),
				NumberFormat.getCurrencyInstance().format(o.getTotal()))));
		
		assertFalse(resultList.isEmpty());
	}
	
	@Test
	void usingDifferent() {
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Order> query = builder.createQuery(Order.class);
		Root<Order> root = query.from(Order.class);
		
		query.select(root);
		
		query.where(
			builder.notEqual(root.get(Order_.status),
				OrderStatus.PAID.getCode()), // and
			builder.notEqual(root.get(Order_.status),
				OrderStatus.CANCELED.getCode()));
		
		TypedQuery<Order> typedQuery = entityManager.createQuery(query);
		List<Order> resultList = typedQuery.getResultList();
		
		resultList.forEach(
			o -> logger.info(String.format("Order: %d - Order Status: %s",
				o.getId(), o.getStatus().getValue())));
		
		assertFalse(resultList.isEmpty());
	}
	
	@Test
	void usingCaseExpression() {
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Object[]> query = builder.createQuery(Object[].class);
		Root<Order> root = query.from(Order.class);
		
		query.multiselect(root.get(Order_.id),
			builder.selectCase(root.get(Order_.status))
				.when(OrderStatus.WAITING.getCode(), "Awaiting")
				.when(OrderStatus.CANCELED.getCode(), "Canceled")
				.when(OrderStatus.PAID.getCode(), "Paid")
				.otherwise("Status undefined"));
		
		TypedQuery<Object[]> typedQuery = entityManager.createQuery(query);
		List<Object[]> list = typedQuery.getResultList();
		
		list.forEach(o -> logger.info(new StringBuilder().append("Order Id: ")
			.append(o[0]).append("; Order Status: ").append(o[1])));
		
		assertFalse(list.isEmpty());
	}
	
	@Test
	void usingCaseExpressionForPaymentType() {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Object[]> criteriaQuery = criteriaBuilder
			.createQuery(Object[].class);
		Root<Order> root = criteriaQuery.from(Order.class);
		Join<Order, Payment> join = root.join(Order_.payment);
		
		criteriaQuery.multiselect(root.get(Order_.id),
			criteriaBuilder
				.selectCase(root.get(Order_.PAYMENT).type().as(Integer.class))
				.when(0, "Bank Slip").when(1, "Credit Card")
				.otherwise("Payment undefined"));
		criteriaQuery.where(criteriaBuilder.equal(join.get(Payment_.status),
			PaymentStatus.PROCESSING.getCode()));
		
		TypedQuery<Object[]> typedQuery = entityManager
			.createQuery(criteriaQuery);
		List<Object[]> list = typedQuery.getResultList();
		
		list.forEach(o -> logger.info(new StringBuilder().append("Order Id: ")
			.append(o[0]).append("; Paid with: ").append(o[1])));
		
		assertFalse(list.isEmpty());
	}
	
	@Test
	void usingExpressionIn() {
		
		List<Integer> listIds = Arrays.asList(1, 3, 4, 6, 7);
		
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Order> criteriaQuery = criteriaBuilder
			.createQuery(Order.class);
		Root<Order> root = criteriaQuery.from(Order.class);
		
		criteriaQuery.select(root);
		
		criteriaQuery.where(root.get(Order_.id).in(listIds));
		
		TypedQuery<Order> typedQuery = entityManager.createQuery(criteriaQuery);
		List<Order> orders = typedQuery.getResultList();
		
		orders.forEach(o -> logger
			.info(String.format("%d - %s", o.getId(), o.getStatus())));
		
		assertFalse(orders.isEmpty());
	}
	
	@Test
	void usingExpressionIn2() {
		Person person1 = entityManager.find(Person.class, 1L);
		Person person2 = entityManager.find(Person.class, 2L);
		
		List<Person> list = Arrays.asList(person1, person2);
		
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Order> criteriaQuery = criteriaBuilder
			.createQuery(Order.class);
		Root<Order> root = criteriaQuery.from(Order.class);
		
		criteriaQuery.select(root);
		
		criteriaQuery.where(root.get(Order_.person).in(list));
		
		TypedQuery<Order> typedQuery = entityManager.createQuery(criteriaQuery);
		List<Order> orders = typedQuery.getResultList();
		
		orders.forEach(o -> logger.info(
			String.format("%d - %s", o.getId(), o.getStatus().getValue())));
		
		assertFalse(orders.isEmpty());
	}
	
}
