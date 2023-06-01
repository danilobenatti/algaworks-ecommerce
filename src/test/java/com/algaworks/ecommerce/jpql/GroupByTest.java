package com.algaworks.ecommerce.jpql;

import static org.junit.jupiter.api.Assertions.assertFalse;

import java.util.List;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import com.algaworks.ecommerce.EntityManagerTest;

import jakarta.persistence.TypedQuery;

class GroupByTest extends EntityManagerTest {
	
	private void test(String jpql) {
		TypedQuery<Object[]> typedQuery = entityManager.createQuery(jpql,
			Object[].class);
		
		List<Object[]> resultList = typedQuery.getResultList();
		assertFalse(resultList.isEmpty());
		
		resultList.forEach(
			i -> logger.info(String.format("%s, %s", i[0], i[1].toString())));
	}
	
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
		test(jpql);
		
	}
	
	@ParameterizedTest
	@ValueSource(strings = {
		"select date_format(o.dateCreate, '%Y/%M'), sum(o.total) from Order o "
			+ "where year(o.dateCreate) = year(current_date) and o.status = 1 "
			+ "group by year(o.dateCreate), month(o.dateCreate)",
		"select c.name, sum(i.subtotal) from OrderItem i join i.product p join p.categories c join i.order o "
			+ "where year(o.dateCreate) = year(current_date) - 1 and month(o.dateCreate) = month(current_date)"
			+ "group by c.id",
		"select p.firstname, sum(o.total) from Order o join o.person p "
			+ "where year(o.dateCreate) = year(current_date) and month(o.dateCreate) >= (month(current_date) - 3) "
			+ "group by p.id" })
	void groupAndFilterResult(String jpql) {
		test(jpql);
		
	}
	
	@ParameterizedTest
	@ValueSource(strings = {
		"select c.name, sum(i.subtotal) from OrderItem i join i.product p join p.categories c "
			+ "group by c.id having sum(i.subtotal) > 1000" })
	void conditionGroupingWithHaving(String jpql) {
		// total sales in top selling categories
		test(jpql);
	}
	
}
