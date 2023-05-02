package com.algaworks.ecommerce.jpql;

import static org.junit.jupiter.api.Assertions.assertFalse;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import com.algaworks.ecommerce.EntityManagerTest;
import com.algaworks.ecommerce.model.Order;

import jakarta.persistence.TypedQuery;

class FunctionsTest extends EntityManagerTest {
	
	static Logger logger = Logger.getLogger(FunctionsTest.class.getName());
	
	private void extracted(String jpql) {
		
		TypedQuery<Object[]> typedQuery = entityManager.createQuery(jpql,
			Object[].class);
		List<Object[]> resultList = typedQuery.getResultList();
		
		assertFalse(resultList.isEmpty());
		
		resultList.forEach(
			i -> logger.log(Level.INFO, i[0] + " - " + i[1] + " - " + i[2]));
	}
	
	@ParameterizedTest
	@ValueSource(strings = {
		"select c.id, c.name, length(c.name) from Category c where length(c.name) > 10 and substring(lower(c.name), 1, 1) = 'e'" })
	void applyFunctionString(String jpql) {
		// concat, length, locate, substring, lower, upper, trim
//		String jpql = "select c.name, concat('Category: ', c.name) from Category c";
//		String jpql = "select c.name, length(c.name) from Category c";
//		String jpql = "select c.name, locate('a', c.name) from Category c";
//		String jpql = "select c.name, substring(c.name, 1, 2) from Category c";
//		String jpql = "select c.name, lower(c.name) from Category c";
//		String jpql = "select c.name, upper(c.name) from Category c";
//		String jpql = "select c.name, trim(c.name) from Category c";
//		String jpql = "select c.name, length(c.name) from Category c";
//		String jpql = "select c.name, length(c.name) from Category c where length(c.name) > 10";
//		String jpql = "select c.name, length(c.name) from Category c where substring(lower(c.name), 1, 1) = 'e'";
//		String jpql = "select c.id, c.name, length(c.name) from Category c where length(c.name) > 10 and substring(lower(c.name), 1, 1) = 'e'";
		
		extracted(jpql);
		
	}
	
	@ParameterizedTest
	@ValueSource(strings = {
		"select current_date, current_time, current_timestamp from Order o where o.dateCreate < current_date",
		"select year(current_time), month(current_time), day(current_time) from Order o",
		"select year(o.dateCreate), month(o.dateCreate), day(o.dateCreate) from Order o",
		"select hour(o.dateCreate), minute(o.dateCreate), second(o.dateCreate) from Order o" })
	void applyFunctionDate(String jpql) {
//		TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
		extracted(jpql);
		
	}
	
	@ParameterizedTest
	@ValueSource(strings = {
		"select abs(-10), mod(3,2), sqrt(25) from Order o where abs(o.total) > 100" })
	void applyFunctionNumber(String jpql) {
		extracted(jpql);
	}
	
	@Test
	void applyFunctionCollection() {
		String jpql = "select size(o.orderitems) from Order o where size(o.orderitems) > 1";
		
		TypedQuery<Integer> typedQuery = entityManager.createQuery(jpql,
			Integer.class);
		List<Integer> resultList = typedQuery.getResultList();
		
		assertFalse(resultList.isEmpty());
		
		resultList.forEach(i -> logger.log(Level.INFO, "{0}", i.intValue()));
		
	}
	
	@Test
	void applyFunctionNative() {
		String jpql = "select o from Order o where function('calc_average_invoicing', o.total) = 1";
		
		TypedQuery<Order> typedQuery = entityManager.createQuery(jpql,
			Order.class);
		List<Order> resultList = typedQuery.getResultList();
		
		assertFalse(resultList.isEmpty());
		
		resultList.forEach(i -> logger.log(Level.INFO, "{0}", i.getClass()));
	}
	
}
