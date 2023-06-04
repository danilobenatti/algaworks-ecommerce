package com.algaworks.ecommerce.nativequeries;

import static org.junit.jupiter.api.Assertions.assertFalse;

import java.text.NumberFormat;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import com.algaworks.ecommerce.EntityManagerTest;
import com.algaworks.ecommerce.model.Product;

import jakarta.persistence.Query;

class NativeQueryTest extends EntityManagerTest {
	
	NumberFormat currency = NumberFormat.getCurrencyInstance();
	
	@SuppressWarnings("unchecked")
	@Test
	void executeSQLReturnArraysList() {
		String sql = "select p.id, p.col_name from tbl_products p";
		
		Query query = entityManager.createNativeQuery(sql);
		
		List<Object[]> list = query.getResultList();
		
		list.forEach(i -> logger.info(String.format("%d - %s", i[0], i[1])));
		
		assertFalse(list.isEmpty());
	}
	
	@SuppressWarnings("unchecked")
	@ParameterizedTest
	@ValueSource(strings = {
		"select id, col_name, col_description, col_image, col_unit, "
			+ "col_unitprice, col_date_create, col_date_update from tbl_products",
		"select id, col_name, col_description, col_image, col_unit, "
			+ "col_unitprice, col_date_create, col_date_update from tbl_product_shop",
		"select prd_id id, prd_name col_name, prd_description col_description, prd_image col_image, prd_unit col_unit, "
			+ "prd_unitprice col_unitprice, prd_date_create col_date_create, prd_date_update col_date_update from tbl_ecm_products",
		"select id, col_name, col_description, null col_image, col_unit, "
			+ "col_unitprice, null col_date_create, null col_date_update from tbl_erp_products" })
	void executeSQLReturnEntity(String sql) {
		Query query = entityManager.createNativeQuery(sql, Product.class);
		
		List<Product> list = query.getResultList();
		
		list.forEach(i -> logger.info(new StringBuilder().append("id: ")
			.append(i.getId()).append("; Product: ").append(i.getName())
			.append("; Price: ").append(currency.format(i.getUnitPrice()))));
		
		assertFalse(list.isEmpty());
	}
	
}