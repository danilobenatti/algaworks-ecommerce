package com.algaworks.ecommerce.jpql;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.math.BigDecimal;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import com.algaworks.ecommerce.EntityManagerTest;

import jakarta.persistence.TypedQuery;

class FunctionsTest extends EntityManagerTest {
	
	private List<Object[]> ResultList(String jpql) {
		TypedQuery<Object[]> typedQuery = entityManager.createQuery(jpql,
			Object[].class);
		List<Object[]> list = typedQuery.getResultList();
		return list;
	}
	
	@ParameterizedTest
	@ValueSource(strings = { "select avg(o.total) from Order o",
		"select count(o) from Order o", "select min(o.total) from Order o",
		"select max(o.total) from Order o",
		"select sum(o.total) from Order o" })
	void applyFunctionAggregation(String jpql) {
		// avg, count, min, max, sum
		TypedQuery<Object> typedQuery = entityManager.createQuery(jpql,
			Object.class);
		Object object = typedQuery.getSingleResult();
		String msg = switch (object) {
			case Double d -> String.format("%.2f", d.doubleValue());
			case Long l -> String.format("%d", l.longValue());
			case BigDecimal b -> String.format("%.2f", b.floatValue());
			default -> object.toString();
		};
		logger.info("Result: " + msg);
		assertNotNull(object);
	}
	
	@ParameterizedTest
	@ValueSource(strings = {
		"select c.name, concat('Category: ', c.name) from Category c",
		"select c.name, length(c.name) from Category c",
		"select c.name, locate('a', c.name) from Category c",
		"select c.name, substring(c.name, 1, 2) from Category c",
		"select c.name, lower(c.name) from Category c",
		"select c.name, upper(c.name) from Category c",
		"select c.name, trim(c.name) from Category c",
		"select c.name, length(c.name) from Category c",
		"select c.name, length(c.name) from Category c where length(c.name) > 10",
		"select c.name, length(c.name) from Category c where substring(lower(c.name), 1, 1) = 'e'",
		"select c.name, length(c.name) from Category c where length(c.name) > 10 and substring(lower(c.name), 1, 1) = 'e'" })
	void applyFunctionString(String jpql) {
		// concat, length, locate, substring, lower, upper, trim
		List<Object[]> list = ResultList(jpql);
		list.forEach(i -> logger.info(String.format("%s - %s", i[0], i[1])));
		assertFalse(list.isEmpty());
	}
	
	@ParameterizedTest
	@ValueSource(strings = {
		"select current_date, current_time, current_timestamp from Order o where o.dateCreate < current_date",
		"select year(current_time), month(current_time), day(current_time) from Order o",
		"select year(o.dateCreate), month(o.dateCreate), day(o.dateCreate) from Order o",
		"select hour(o.dateCreate), minute(o.dateCreate), second(o.dateCreate) from Order o" })
	void applyFunctionDate(String jpql) {
//		TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
		List<Object[]> list = ResultList(jpql);
		list.forEach(
			i -> logger.info(String.format("%s - %s - %s", i[0], i[1], i[2])));
		assertFalse(list.isEmpty());
	}
	
	@ParameterizedTest
	@ValueSource(strings = {
		"select abs(-10), mod(3,2), sqrt(25) from Order o where abs(o.total) > 100" })
	void applyFunctionNumber(String jpql) {
		List<Object[]> list = ResultList(jpql);
		list.forEach(
			i -> logger.info(String.format("%s - %s - %s", i[0], i[1], i[2])));
		assertFalse(list.isEmpty());
	}
	
	@Test
	void applyFunctionCollection() {
		String jpql = "select size(o.orderitems) from Order o where size(o.orderitems) >= 1";
		
		TypedQuery<Number> typedQuery = entityManager.createQuery(jpql,
			Number.class);
		List<Number> resultList = typedQuery.getResultList();
		
		resultList.forEach(i -> logger.info(i.intValue()));
		assertFalse(resultList.isEmpty());
	}
	
	@ParameterizedTest
	@ValueSource(strings = {
		"select o from Order o where function('calc_average_invoicing', o.total) = 1",
		"select function('dayname', o.dateCreate) from Order o where function('calc_average_invoicing', o.total) = 1" })
	void applyFunctionNative(String jpql) {
		/**
		 * CREATE FUNCTION calc_average_invoicing(value double) RETURNS BOOLEAN
		 * READS SQL DATA RETURN value > (select avg(col_total) from
		 * tbl_orders);
		 * 
		 * select dayname(o.col_date_create) as result from tbl_orders as o
		 * where calc_average_invoicing(o.col_total) = 1;
		 */
		
		TypedQuery<?> typedQuery = entityManager.createQuery(jpql,
			Object.class);
		List<?> resultList = typedQuery.getResultList();
		
		resultList.forEach(i -> logger.info(i.toString()));
		assertFalse(resultList.isEmpty());
	}
	
}
