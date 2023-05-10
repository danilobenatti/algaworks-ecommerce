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
	
}
