package com.algaworks.ecommerce.nativequeries;

import static org.junit.jupiter.api.Assertions.assertFalse;

import java.text.NumberFormat;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.algaworks.ecommerce.EntityManagerTest;
import com.algaworks.ecommerce.model.Person;

import jakarta.persistence.Query;

class ViewTest extends EntityManagerTest {
	
	NumberFormat currency = NumberFormat.getCurrencyInstance();
	
	@SuppressWarnings("unchecked")
	@Test
	void executeView() {
		String sql = "select p.person_id, p.col_firstname, sum(o.col_total) from tbl_orders o "
			+ "join purchases_above_average_by_current_year p on p.person_id = o.person_id "
			+ "group by o.person_id";
		Query query = entityManager.createNativeQuery(sql);
		
		List<Object[]> list = query.getResultList();
		
		list.forEach(o -> logger.info(new StringBuilder().append("Person Id: ")
			.append(o[0]).append("; Firstname: ").append(o[1])
			.append("; Total: ").append(currency.format(o[2]))));
		
		assertFalse(list.isEmpty());
	}
	
	@SuppressWarnings("unchecked")
	@Test
	void executeViewReturnClient() {
		String sql = "select * from purchases_above_average_by_current_year";
		Query query = entityManager.createNativeQuery(sql, Person.class);
		
		List<Person> list = query.getResultList();
		
		list.forEach(p -> logger
			.info(new StringBuilder().append("Person Id: ").append(p.getId())
				.append("; Firstname: ").append(p.getFirstname())));
		
		assertFalse(list.isEmpty());
	}
}
