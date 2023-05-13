package com.algaworks.ecommerce.jpql;

import static org.junit.jupiter.api.Assertions.assertFalse;

import java.math.BigDecimal;
import java.util.List;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import com.algaworks.ecommerce.EntityManagerTest;
import com.algaworks.ecommerce.model.Order;
import com.algaworks.ecommerce.model.Person;
import com.algaworks.ecommerce.model.Product;

import jakarta.persistence.TypedQuery;

class SubQueriesTest extends EntityManagerTest {
	
	static Logger logger = Logger.getLogger(SubQueriesTest.class.getName());
	
	@ParameterizedTest
	@ValueSource(strings = {
		"select p from Person p where 600 < (select sum(o.total) from p.orders o)",
		"select p from Person p where 600 < (select sum(o.total) from Order o where o.person = p)",
		"select o from Order o where o.total > (select round(avg(total), 2) from Order)",
		"select p from Product p where p.unitPrice = (select round(max(unitPrice), 2) from Product)" })
	void searchSubQueries(String jpql) {
		// Good customers(person) v1
		// Good customers(person) v2
		// All orders above average sales
		// The most expensive product(s)
		TypedQuery<Object> typedQuery = entityManager.createQuery(jpql,
			Object.class);
		
		List<Object> resultList = typedQuery.getResultList();
		assertFalse(resultList.isEmpty());
		
		resultList.stream()
			.forEach(i -> logger.log(Level.INFO, "{0}", message(i)));
		
	}
	
	@SuppressWarnings("preview")
	public static String message(Object object) {
		Locale locale = new Locale.Builder().setLanguage("pt").setRegion("BR")
			.build();
		return switch (object) {
			case Person c -> String.format(locale, "%d -> %s, sum: %.2f",
				c.getId(), c.getFirstname(),
				c.getOrders().stream().map(Order::getTotal)
					.reduce(BigDecimal.ZERO, BigDecimal::add));
			case Order o -> String.format(locale, "%d -> %s, total: %.2f",
				o.getId(), o.getPerson().getFirstname(), o.getTotal());
			case Product p -> String.format(locale, "%d -> %s, price: %.2f",
				p.getId(), p.getName(), p.getUnitPrice());
			case null -> "It is a null object";
			default -> object.toString();
		};
	}
	
	@ParameterizedTest
	@ValueSource(strings = { "select o from Order o where o.id in (1, 2, 3, 4)",
		"select distinct o from Order o join o.orderitems i join i.product p where p.unitPrice > 500.0",
		"select o from Order o where o.id in ("
			+ "select o2.id from OrderItem i2 join i2.order o2 join i2.product p2 "
			+ "where p2.unitPrice > 500.0)" })
	void searchWithIn(String jpql) {
		TypedQuery<Order> typedQuery = entityManager.createQuery(jpql,
			Order.class);
		List<Order> resultList = typedQuery.getResultList();
		assertFalse(resultList.isEmpty());
		
		resultList.forEach(i -> logger.log(Level.INFO, "ID: {0}", i.getId()));
	}
	
	@ParameterizedTest
	@ValueSource(strings = {
		"select p1 from Product p1 where exists (select true from OrderItem i2 join i2.product p2 where p2 = p1)",
		"select p1 from Product p1 where not exists "
			+ "(select true from OrderItem i2 join i2.product p2 where p2 = p1 and i2.order.status = 3)" })
	void searchExists(String jqpl) {
		TypedQuery<Product> typedQuery = entityManager.createQuery(jqpl,
			Product.class);
		List<Product> resultList = typedQuery.getResultList();
		assertFalse(resultList.isEmpty());
		
		resultList.forEach(p -> logger.log(Level.INFO, "{0}",
			String.format("%d - %s", p.getId(), p.getName())));
	}
	
	@ParameterizedTest
	@ValueSource(strings = {
		"select o1 from Order o1 where o1.id in (select o2.id from OrderItem i2 "
			+ "join i2.order o2 join i2.product p2 "
			+ "join p2.categories c2 where c2.id = 8)",
		"select p from Person p where "
			+ "(select count(o) from Order o where person = p) >= 2",
		"select p1 from Product p1 where exists "
			+ "(select i2.product from OrderItem i2 "
			+ "where i2.product = p1 and i2.subtotal/i2.quantity <> p1.unitPrice)",
		"select p1 from Product p1 where p1.unitPrice = all "
			+ "(select i2.subtotal/i2.quantity from OrderItem i2 where i2.product = p1)",
		"select p1 from Product p1 where p1.unitPrice > all "
			+ "(select i2.subtotal/i2.quantity from OrderItem i2 where i2.product = p1)",
		"select p1 from Product p1 where p1.unitPrice = any "
			+ "(select i2.subtotal/i2.quantity from OrderItem i2 where i2.product = p1)",
		"select p1 from Product p1 where p1.unitPrice <> any " // some
			+ "(select i2.subtotal/i2.quantity from OrderItem i2 where i2.product = p1)",
		"select distinct p1 from OrderItem i1 join i1.product p1 where i1.product.unitPrice = all"
			+ "(select i2.product.unitPrice from OrderItem i2 where i2.product = p1 and i2.id <> i1.id)" })
	void exercises(String jpql) {
		// Find orders that have a specific product.
		// Find persons who placed two or more orders.
		// Find products not sold for the current unit price.
		// Find products that have always sold at the current price. [ALL]
		// Find products that have not sold after the price increased. [ALL]
		// Find products sold at least once at the current price. [ANY]
		// Find products sold at a price different from the current price. [ANY]
		// Find products that are always sold at the same price.
		
		TypedQuery<Object> typedQuery = entityManager.createQuery(jpql,
			Object.class);
		
		List<Object> resultList = typedQuery.getResultList();
		assertFalse(resultList.isEmpty());
		
		resultList.stream().forEach(i -> logger.log(Level.INFO, "{0}", msg(i)));
	}
	
	@SuppressWarnings("preview")
	public static String msg(Object object) {
		Locale locale = new Locale.Builder().setLanguage("pt").setRegion("BR")
			.build();
		return switch (object) {
			case Order o ->
				String.format(locale, "%d - %s", o.getId(), o.getStatus());
			case Person c ->
				String.format(locale, "%d - %s", c.getId(), c.getFirstname());
			case Product p ->
				String.format(locale, "%d - %s", p.getId(), p.getName());
			case null -> "It is a null object";
			default -> object.toString();
		};
	}
	
}
