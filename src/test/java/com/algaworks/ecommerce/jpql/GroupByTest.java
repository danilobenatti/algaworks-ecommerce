package com.algaworks.ecommerce.jpql;

import static org.junit.jupiter.api.Assertions.assertFalse;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import com.algaworks.ecommerce.EntityManagerTest;

import jakarta.persistence.TypedQuery;

class GroupByTest extends EntityManagerTest {
	
	static Logger logger = Logger.getLogger(GroupByTest.class.getName());
	
	// concat(year(o.dateCreate), ', ',monthname(o.dateCreate))
	
	@ParameterizedTest
	@ValueSource(strings = {
		"select c.name, count(p.id) from Category c left join c.products p group by c.id",
		"select date_format(o.dateCreate, '%Y/%M'), sum(o.total) from Order o "
			+ "group by year(o.dateCreate), month(o.dateCreate)",
		"select c.name, sum(i.subtotal) from OrderItem i join i.product p join p.categories c "
			+ "group by c.id",
		"select p.firstname, sum(o.total) from Order o join o.person p group by p.id",
		"select date_format(o.dateCreate, '%d/%m/%Y'), concat(c.name, ': ', sum(i.subtotal)) from OrderItem i "
			+ "join i.order o join i.product p join p.categories c "
			+ "group by year(o.dateCreate), month(o.dateCreate), day(o.dateCreate), c.id "
			+ "order by o.dateCreate, c.name" })
	void groupResult(String jpql) {
		// quantity of products by category
		// total of sales by month
		// total of sales by category
		// total of sales by person
		// total of sales by day and category -> chalenge code
		TypedQuery<Object[]> typedQuery = entityManager.createQuery(jpql,
			Object[].class);
		List<Object[]> resultList = typedQuery.getResultList();
		
		assertFalse(resultList.isEmpty());
		
		resultList.forEach(i -> logger.log(Level.INFO, "{0}",
			String.format("%s, %s", i[0], i[1].toString())));
		
	}
	
	@ParameterizedTest
	@ValueSource(strings = {
		"select date_format(o.dateCreate, '%Y/%M'), sum(o.total) from Order o "
			+ "where year(o.dateCreate) = year(current_date) and o.status = 1 "
			+ "group by year(o.dateCreate), month(o.dateCreate)",
		"select c.name, sum(i.subtotal) from OrderItem i join i.product p join p.categories c join i.order o "
			+ "where year(o.dateCreate) = year(current_date) and month(o.dateCreate) = month(current_date) "
			+ "group by c.id",
		"select p.firstname, sum(o.total) from Order o join o.person p "
			+ "where year(o.dateCreate) = year(current_date) and month(o.dateCreate) >= (month(current_date) - 3)"
			+ "group by p.id " })
	void groupAndFilterResult(String jpql) {
		// total of sales by month into last year and status 1 (Waiting)
		// total of sales by category into last year and last month
		// total of sales by person in the last 3 months
		TypedQuery<Object[]> typedQuery = entityManager.createQuery(jpql,
			Object[].class);
		
		List<Object[]> resultList = typedQuery.getResultList();
		
		assertFalse(resultList.isEmpty());
		
		resultList.forEach(i -> logger.log(Level.INFO, "{0}",
			String.format("%s, %s", i[0], i[1].toString())));
		
	}
	
}
