package com.algaworks.ecommerce.nativequeries;

import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.Year;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.algaworks.ecommerce.EntityManagerTest;
import com.algaworks.ecommerce.model.Person;

import jakarta.persistence.ParameterMode;
import jakarta.persistence.StoredProcedureQuery;

class StoredProceduresTest extends EntityManagerTest {
	
	@Test
	void usingInAndOutParameters() {
		
		StoredProcedureQuery procedureQuery = entityManager
			.createStoredProcedureQuery("findname_product_by_id");
		
		procedureQuery.registerStoredProcedureParameter("product_id",
			Long.class, ParameterMode.IN);
		procedureQuery.registerStoredProcedureParameter("product_name",
			String.class, ParameterMode.OUT);
		
		procedureQuery.setParameter("product_id", 3L);
		String value = (String) procedureQuery
			.getOutputParameterValue("product_name");
		
		logger.info("Return: " + value);
		
		assertEquals("Câmera GoPro Hero", value);
	}
	
	@SuppressWarnings("unchecked")
	@Test
	void returnDataOfProcedure() {
		
		StoredProcedureQuery procedureQuery = entityManager
			.createStoredProcedureQuery("purchases_above_average_by_year",
				Person.class);
		
		procedureQuery.registerStoredProcedureParameter("year_date", Year.class,
			ParameterMode.IN);
		
		procedureQuery.setParameter("year_date", Year.of(2022));
		
		List<Person> list = procedureQuery.getResultList();
		
		list.forEach(p -> logger.info("Return: " + p.getFirstname()));
		
		assertFalse(list.isEmpty());
	}
	
}
