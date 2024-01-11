package com.algaworks.ecommerce.criteria;

import static org.junit.jupiter.api.Assertions.assertFalse;

import java.math.BigDecimal;
import java.sql.Date;
import java.text.NumberFormat;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.algaworks.ecommerce.EntityManagerTest;
import com.algaworks.ecommerce.model.Order;
import com.algaworks.ecommerce.model.Order_;
import com.algaworks.ecommerce.model.Payment;
import com.algaworks.ecommerce.model.PaymentBankSlip;
import com.algaworks.ecommerce.model.PaymentBankSlip_;
import com.algaworks.ecommerce.model.Person;
import com.algaworks.ecommerce.model.Person_;
import com.algaworks.ecommerce.model.enums.OrderStatus;

import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.ListJoin;
import jakarta.persistence.criteria.Root;

class FunctionsCriteriaTest extends EntityManagerTest {
	
	@Test
	
	void applyingStringFunction() {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Object[]> criteriaQuery = criteriaBuilder
			.createQuery(Object[].class);
		Root<Person> root = criteriaQuery.from(Person.class);
		ListJoin<Person, Order> joinOrder = root.join(Person_.orders);
		
		criteriaQuery.multiselect(root.get(Person_.firstname),
			criteriaBuilder.concat("Person: ", root.get(Person_.firstname)),
			criteriaBuilder.length(root.get(Person_.firstname)),
			criteriaBuilder.locate(root.get(Person_.firstname), "a"),
			criteriaBuilder.substring(root.get(Person_.firstname), 1, 2),
			criteriaBuilder.lower(root.get(Person_.firstname)),
			criteriaBuilder.upper(root.get(Person_.firstname)),
			criteriaBuilder.trim(root.get(Person_.firstname)));
		
		criteriaQuery.where(criteriaBuilder.greaterThanOrEqualTo(
			joinOrder.get(Order_.total), BigDecimal.valueOf(500)));
		
		TypedQuery<Object[]> typedQuery = entityManager
			.createQuery(criteriaQuery);
		List<Object[]> list = typedQuery.getResultList();
		
		list.forEach(p -> logger.info(new StringBuilder().append("Firstname: ")
			.append(p[0]).append("\nconcat: ").append(p[1]).append("\nlength: ")
			.append(p[2]).append("\nlocate: ").append(p[3])
			.append("\nsubstring: ").append(p[4]).append("\nlower: ")
			.append(p[5]).append("\nupper: ").append(p[6]).append("\ntrim: ")
			.append(p[7]).append("\n--- # ---\n")));
		
		assertFalse(list.isEmpty());
	}
	
	@Test
	void applyingDateFunction() {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Object[]> criteriaQuery = criteriaBuilder
			.createQuery(Object[].class);
		Root<Order> root = criteriaQuery.from(Order.class);
		Join<Order, Payment> joinPayment = root.join(Order_.payment);
		Join<Order, PaymentBankSlip> joinPaymentBankSlip = criteriaBuilder
			.treat(joinPayment, PaymentBankSlip.class);
		
		criteriaQuery.multiselect(root.get(Order_.id),
			criteriaBuilder.currentDate(), criteriaBuilder.currentTime(),
			criteriaBuilder.currentTimestamp());
		
		criteriaQuery.where(
			criteriaBuilder.between(criteriaBuilder.currentDate(),
				root.get(Order_.dateCreate).as(Date.class),
				joinPaymentBankSlip.get(PaymentBankSlip_.expirationDate)
					.as(Date.class)), // and
			criteriaBuilder.equal(root.get(Order_.status),
				OrderStatus.WAITING.getCode()));
		
		TypedQuery<Object[]> typedQuery = entityManager
			.createQuery(criteriaQuery);
		List<Object[]> list = typedQuery.getResultList();
		
		list.forEach(o -> logger.info(new StringBuilder().append("Order: ")
			.append(o[0]).append(", CurrentDate: ").append(o[1])
			.append(", CurrentTime: ").append(o[2])
			.append(", CurrentTimestamp: ").append(o[3])));
		
		assertFalse(list.isEmpty());
	}
	
	@Test
	void applyingNumberFunction() {
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Object[]> query = builder.createQuery(Object[].class);
		Root<Order> root = query.from(Order.class);
		
		query.multiselect(root.get(Order_.id),
				builder.abs(builder.prod(root.get(Order_.total), -1)),
				builder.mod(root.get(Order_.total).as(Integer.class), 2),
				builder.sqrt(root.get(Order_.total)));
		query.where(builder.greaterThan(builder.sqrt(root.get(Order_.total)),
				20.0));
		
		TypedQuery<Object[]> typedQuery = entityManager.createQuery(query);
		List<Object[]> list = typedQuery.getResultList();
		
		list.forEach(o -> logger.info(new StringBuilder().append("Order: ")
				.append(o[0]).append(", abs: ").append(o[1]).append(", mod: ")
				.append(o[2]).append(", sqrt: ").append(o[3])));
		
		assertFalse(list.isEmpty());
	}
	
	@Test
	void applyingCollectionFunction() {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Object[]> criteriaQuery = criteriaBuilder
			.createQuery(Object[].class);
		Root<Order> root = criteriaQuery.from(Order.class);
		
		criteriaQuery.multiselect(root.get(Order_.id),
			criteriaBuilder.size(root.get(Order_.orderitems)),
			root.get(Order_.total));
		criteriaQuery.where(criteriaBuilder.greaterThanOrEqualTo(
			criteriaBuilder.size(root.get(Order_.orderitems)), 2));
		
		TypedQuery<Object[]> typedQuery = entityManager
			.createQuery(criteriaQuery);
		List<Object[]> list = typedQuery.getResultList();
		
		NumberFormat currency = NumberFormat.getCurrencyInstance();
		list.forEach(o -> logger.info(new StringBuilder().append("Order ID: ")
			.append(o[0]).append("; Qtd Items: ").append(o[1])
			.append("; Total: ").append(currency.format(o[2]))));
		
		assertFalse(list.isEmpty());
	}
	
	@Test
	void applyingNativeFunction() {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Object[]> criteriaQuery = criteriaBuilder
			.createQuery(Object[].class);
		Root<Order> root = criteriaQuery.from(Order.class);
		
		criteriaQuery.multiselect(
			root.get(Order_.id), criteriaBuilder.function("dayname",
				String.class, root.get(Order_.dateCreate)),
			root.get(Order_.total));
		criteriaQuery.where(criteriaBuilder.isTrue(criteriaBuilder.function(
			"calc_average_invoicing", Boolean.class, root.get(Order_.total))));
		
		TypedQuery<Object[]> typedQuery = entityManager
			.createQuery(criteriaQuery);
		List<Object[]> list = typedQuery.getResultList();
		
		NumberFormat currency = NumberFormat.getCurrencyInstance();
		list.forEach(o -> logger.info(new StringBuilder().append("Order ID: ")
			.append(o[0]).append("; Weekday: ").append(o[1]).append("; Total: ")
			.append(currency.format(o[2]))));
		
		assertFalse(list.isEmpty());
	}
	
	@Test
	void applyingAggregationFunction() {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Object[]> criteriaQuery = criteriaBuilder
			.createQuery(Object[].class);
		Root<Order> root = criteriaQuery.from(Order.class);
		
		criteriaQuery.multiselect(criteriaBuilder.sum(root.get(Order_.total)),
			criteriaBuilder.min(root.get(Order_.total)),
			criteriaBuilder.max(root.get(Order_.total)),
			criteriaBuilder.avg(root.get(Order_.total)),
			criteriaBuilder.count(root.get(Order_.id)));
		
		TypedQuery<Object[]> typedQuery = entityManager
			.createQuery(criteriaQuery);
		List<Object[]> list = typedQuery.getResultList();
		
		NumberFormat currency = NumberFormat.getCurrencyInstance();
		list.forEach(o -> logger.info(new StringBuilder().append("total: ")
			.append(currency.format(o[0])).append("; min: ")
			.append(currency.format(o[1])).append("; max: ")
			.append(currency.format(o[2])).append("; average: ")
			.append(currency.format(o[3])).append("; count: ").append(o[4])));
		
		assertFalse(list.isEmpty());
	}
}
