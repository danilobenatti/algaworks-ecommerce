package com.algaworks.ecommerce.criteria;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import org.junit.jupiter.api.Test;

import com.algaworks.ecommerce.EntityManagerTest;
import com.algaworks.ecommerce.model.Invoice;
import com.algaworks.ecommerce.model.Order;

import jakarta.persistence.TemporalType;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.ParameterExpression;
import jakarta.persistence.criteria.Root;

class PassingParametersCriteriaTest extends EntityManagerTest {
	
	@Test
	void passingParametersCriteria() {
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Order> query = builder.createQuery(Order.class);
		Root<Order> root = query.from(Order.class);
		
		query.select(root);
		ParameterExpression<Long> parameter = builder.parameter(Long.class,
			"id");
		query.where(builder.equal(root.get("id"), parameter));
		/*
		 * String jpql = "select o from Order o where o.id = 1";
		 */
		TypedQuery<Order> typedQuery = entityManager.createQuery(query);
		typedQuery.setParameter("id", 1L);
		
		Order order = typedQuery.getSingleResult();
		
		assertNotNull(order);
	}
	
	@Test
	void passingParamDateCriteria() {
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Invoice> query = builder.createQuery(Invoice.class);
		Root<Invoice> root = query.from(Invoice.class);
		
		query.select(root);
		query.where(builder.greaterThan(root.get("issuedatetime"),
			builder.parameter(Date.class, "dateSearch")));
		/*
		 * String jpql = "select i from Invoice i where i.issuedatetime = 1";
		 */
		TypedQuery<Invoice> typedQuery = entityManager.createQuery(query);
		
		Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC-3"),
			new Locale.Builder().setLanguage("pt").setRegion("BR").build());
		calendar.add(Calendar.MONTH, -10); // minus 10 months
		
		typedQuery.setParameter("dateSearch", calendar.getTime(),
			TemporalType.TIMESTAMP);
		
		List<Invoice> invoice = typedQuery.getResultList();
		
		assertFalse(invoice.isEmpty());
	}
	
}
