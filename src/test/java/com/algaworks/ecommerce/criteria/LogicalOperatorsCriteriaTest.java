package com.algaworks.ecommerce.criteria;

import static org.junit.jupiter.api.Assertions.assertFalse;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.algaworks.ecommerce.EntityManagerTest;
import com.algaworks.ecommerce.model.Order;
import com.algaworks.ecommerce.model.Order_;
import com.algaworks.ecommerce.model.Payment;
import com.algaworks.ecommerce.model.Payment_;
import com.algaworks.ecommerce.model.enums.PaymentStatus;

import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Root;

class LogicalOperatorsCriteriaTest extends EntityManagerTest {
	
	@Test
	void usingOperatorsGreaterThanAndEqual() {
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Order> query = builder.createQuery(Order.class);
		Root<Order> root = query.from(Order.class);
		Join<Order, Payment> joinPayment = root.join(Order_.payment);
		
		// select o from Order o join Payment p where (p.status = 1 or
		// p.status = 3) and o.total > 499.5
		
		query.select(root);
		
		query.where(builder.and(
			builder.or(
				builder.equal(joinPayment.get(Payment_.status),
					PaymentStatus.PROCESSING.getCode()),
				builder.equal(joinPayment.get(Payment_.status),
					PaymentStatus.RECEIVED.getCode())),
			builder.greaterThan(root.get(Order_.total),
				BigDecimal.valueOf(499.5))));
		
		TypedQuery<Order> typedQuery = entityManager.createQuery(query);
		List<Order> resultList = typedQuery.getResultList();
		
		NumberFormat currency = NumberFormat.getCurrencyInstance();
		
		resultList.forEach(o -> logger
			.info(String.format("Order: %d - Payment Status: %s - Total: %s",
				o.getId(), o.getPayment().getStatus(),
				currency.format(o.getTotal().doubleValue()))));
		
		assertFalse(resultList.isEmpty());
	}
}
