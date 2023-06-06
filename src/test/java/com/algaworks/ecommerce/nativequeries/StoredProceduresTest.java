package com.algaworks.ecommerce.nativequeries;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import com.algaworks.ecommerce.EntityManagerTest;

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
	
}
