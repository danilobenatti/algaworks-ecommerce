package com.algaworks.ecommerce.criteria;

import static org.junit.jupiter.api.Assertions.assertFalse;

import java.math.BigDecimal;
import java.sql.Date;
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
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Object[]> query = builder.createQuery(Object[].class);
		Root<Person> root = query.from(Person.class);
		ListJoin<Person, Order> joinOrder = root.join(Person_.orders);
		
		query.multiselect(root.get(Person_.firstname),
			builder.concat("Person: ", root.get(Person_.firstname)),
			builder.length(root.get(Person_.firstname)),
			builder.locate(root.get(Person_.firstname), "a"),
			builder.substring(root.get(Person_.firstname), 1, 2),
			builder.lower(root.get(Person_.firstname)),
			builder.upper(root.get(Person_.firstname)),
			builder.trim(root.get(Person_.firstname)));
		
		query.where(builder.greaterThanOrEqualTo(joinOrder.get(Order_.total),
			BigDecimal.valueOf(500)));
		
		TypedQuery<Object[]> typedQuery = entityManager.createQuery(query);
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
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Object[]> criteriaQuery = criteriaBuilder
			.createQuery(Object[].class);
		Root<Order> root = criteriaQuery.from(Order.class);
		
		criteriaQuery.multiselect(root.get(Order_.id),
			criteriaBuilder
				.abs(criteriaBuilder.prod(root.get(Order_.total), -1)),
			criteriaBuilder.mod(root.get(Order_.TOTAL), 2),
			criteriaBuilder.sqrt(root.get(Order_.total)));
		criteriaQuery.where(criteriaBuilder
			.greaterThan(criteriaBuilder.sqrt(root.get(Order_.total)), 20.0));
		
		TypedQuery<Object[]> typedQuery = entityManager
			.createQuery(criteriaQuery);
		List<Object[]> list = typedQuery.getResultList();
		
		list.forEach(o -> logger.info(new StringBuilder().append("Order: ")
			.append(o[0]).append(", abs: ").append(o[1]).append(", mod: ")
			.append(o[2]).append(", sqrt: ").append(o[3])));
		
		assertFalse(list.isEmpty());
	}
}
