package com.algaworks.ecommerce.nativequeries;

import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.time.Year;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.algaworks.ecommerce.EntityManagerTest;
import com.algaworks.ecommerce.model.Person;
import com.algaworks.ecommerce.model.Product;

import jakarta.persistence.ParameterMode;
import jakarta.persistence.StoredProcedureQuery;

class StoredProceduresTest extends EntityManagerTest {
	
	NumberFormat cf = NumberFormat.getCurrencyInstance();
	
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
	
	@Test
	void updateProductPriceExercise() {
		
		StoredProcedureQuery procedureQuery = entityManager
			.createStoredProcedureQuery("adjust_price_product", Product.class);
		
		procedureQuery.registerStoredProcedureParameter("product_id",
			Long.class, ParameterMode.IN);
		procedureQuery.registerStoredProcedureParameter("percentage",
			Double.class, ParameterMode.IN);
		procedureQuery.registerStoredProcedureParameter("newUnitPrice",
			BigDecimal.class, ParameterMode.OUT);
		
		procedureQuery.setParameter("product_id", 1L);
		procedureQuery.setParameter("percentage", Double.valueOf(-5.0)); // 5%_off
		
		BigDecimal newPrice = (BigDecimal) procedureQuery
			.getOutputParameterValue("newUnitPrice");
		
		// 799.5 - 5% = 759.53
		Product p1 = entityManager.find(Product.class, 1L);
		
		logger.info(new StringBuilder().append("Product Id: ")
			.append(p1.getId()).append("; Name: ").append(p1.getName())
			.append("; New UnitPrice: ").append(cf.format(p1.getUnitPrice())));
		
		assertEquals(BigDecimal.valueOf(759.53), p1.getUnitPrice());
		assertEquals(newPrice, p1.getUnitPrice());
	}
	
}
