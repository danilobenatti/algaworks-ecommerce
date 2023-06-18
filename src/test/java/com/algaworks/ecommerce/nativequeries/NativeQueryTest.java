package com.algaworks.ecommerce.nativequeries;

import static org.junit.jupiter.api.Assertions.assertFalse;

import java.text.NumberFormat;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import com.algaworks.ecommerce.EntityManagerTest;
import com.algaworks.ecommerce.dto.CategoryDTO;
import com.algaworks.ecommerce.dto.ProductDTO;
import com.algaworks.ecommerce.model.Category;
import com.algaworks.ecommerce.model.OrderItem;
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
		"select id, col_version, col_name, col_description, col_image, col_unit, col_unitprice, "
			+ "col_active, col_date_create, col_date_update from tbl_products",
		"select id, col_version, col_name, col_description, col_image, col_unit, col_unitprice, "
			+ "col_active, col_date_create, col_date_update from tbl_product_shop",
		"select prd_id id, prd_version col_version, prd_name col_name, prd_description col_description, "
			+ "prd_image col_image, prd_unit col_unit, prd_unitprice col_unitprice, "
			+ "prd_active col_active, prd_date_create col_date_create, prd_date_update col_date_update from tbl_ecm_products",
		"select id, col_version, col_name, col_description, null col_image, col_unit, col_unitprice, "
			+ "col_active, null col_date_create, null col_date_update from tbl_erp_products" })
	void executeSQLReturnEntity(String sql) {
		Query query = entityManager.createNativeQuery(sql, Product.class);
		
		List<Product> list = query.getResultList();
		
		list.forEach(i -> logger.info(new StringBuilder().append("id: ")
			.append(i.getId()).append("; Product: ").append(i.getName())
			.append("; Price: ").append(currency.format(i.getUnitPrice()))));
		
		assertFalse(list.isEmpty());
	}
	
	@SuppressWarnings("unchecked")
	@Test
	void executeSQLReturnEntityById() {
		String sql = "select prd_id id, prd_version col_version, prd_name col_name, prd_description col_description, "
			+ "prd_image col_image, prd_unit col_unit, prd_unitprice col_unitprice, "
			+ "prd_date_create col_date_create, prd_date_update col_date_update, "
			+ "prd_active col_active from tbl_ecm_products where prd_id = :id";
		Query query = entityManager.createNativeQuery(sql, Product.class);
		query.setParameter("id", Long.valueOf(201));
		
		List<Product> list = query.getResultList();
		
		list.forEach(i -> logger.info(new StringBuilder().append("id: ")
			.append(i.getId()).append("; Product: ").append(i.getName())
			.append("; Price: ").append(currency.format(i.getUnitPrice()))));
		
		assertFalse(list.isEmpty());
	}
	
	@SuppressWarnings("unchecked")
	@Test
	void usingSQLResultSetMapping1() {
		String sql = "select id, col_version, col_name, col_description, col_image, col_unit, col_unitprice, "
			+ "col_active, col_date_create, col_date_update from tbl_product_shop";
		
		Query query = entityManager.createNativeQuery(sql,
			"product_shop.Product");
		List<Product> list = query.getResultList();
		
		list.forEach(i -> logger.info(new StringBuilder().append("id: ")
			.append(i.getId()).append("; Product: ").append(i.getName())
			.append("; Price: ").append(currency.format(i.getUnitPrice()))));
		
		assertFalse(list.isEmpty());
	}
	
	@SuppressWarnings("unchecked")
	@Test
	void usingSQLResultSetMapping2() {
		String sql = "select i.*, p.* from tbl_order_items i "
			+ "join tbl_products p on p.id = i.product_id";
		
		Query query = entityManager.createNativeQuery(sql,
			"orderitem_product.OrderItem-Product");
		List<Object[]> list = query.getResultList();
		
		list.forEach(i -> logger.info(new StringBuilder().append("Order Id: ")
			.append(((OrderItem) i[0]).getId().getOrderId())
			.append("; Product Id: ").append(((Product) i[1]).getId())
			.append("; Name: ").append(((Product) i[1]).getName())
			.append("; Price: ")
			.append(currency.format(((Product) i[1]).getUnitPrice()))));
		
		assertFalse(list.isEmpty());
	}
	
	@SuppressWarnings("unchecked")
	@Test
	void usingFieldResult() {
		String sql = "select * from tbl_ecm_products";
		
		Query query = entityManager.createNativeQuery(sql,
			"ecm_products.Product");
		List<Product> list = query.getResultList();
		
		list.forEach(i -> logger.info(new StringBuilder().append("id: ")
			.append(i.getId()).append("; Product: ").append(i.getName())
			.append("; Price: ").append(currency.format(i.getUnitPrice()))));
		
		assertFalse(list.isEmpty());
	}
	
	@SuppressWarnings("unchecked")
	@Test
	void usingColumnResultFromDto() {
		String sql = "select * from tbl_ecm_products";
		
		Query query = entityManager.createNativeQuery(sql,
			"ecm_products.ProductDTO");
		List<ProductDTO> list = query.getResultList();
		
		list.forEach(i -> logger.info(new StringBuilder().append("id: ")
			.append(i.getId()).append("; Product: ").append(i.getName())));
		
		assertFalse(list.isEmpty());
	}
	
	@SuppressWarnings("unchecked")
	@Test
	void usingNamedNativeQuery1() {
		Query query = entityManager.createNamedQuery("product_shop.listAll");
		
		List<Product> list = query.getResultList();
		
		list.forEach(i -> logger.info(new StringBuilder().append("id: ")
			.append(i.getId()).append("; Product: ").append(i.getName())
			.append("; Price: ").append(currency.format(i.getUnitPrice()))));
		
		assertFalse(list.isEmpty());
	}
	
	@SuppressWarnings("unchecked")
	@Test
	void usingNamedNativeQuery2() {
		Query query = entityManager.createNamedQuery("ecm_products.listAll");
		
		List<Product> list = query.getResultList();
		
		list.forEach(i -> logger.info(new StringBuilder().append("id: ")
			.append(i.getId()).append("; Product: ").append(i.getName())
			.append("; Price: ").append(currency.format(i.getUnitPrice()))));
		
		assertFalse(list.isEmpty());
	}
	
	@SuppressWarnings("unchecked")
	@Test
	void usingFileXML() {
		Query query = entityManager.createNamedQuery("ecm_category.listAll");
		
		List<Category> list = query.getResultList();
		
		list.forEach(i -> logger.info(new StringBuilder().append("id: ")
			.append(i.getId()).append("; Category: ").append(i.getName())));
		
		assertFalse(list.isEmpty());
	}
	
	@SuppressWarnings("unchecked")
	@Test
	void usingFileXMLWithCategoryDTO() {
		Query query = entityManager
			.createNamedQuery("ecm_category.listAll.dto");
		
		List<CategoryDTO> list = query.getResultList();
		
		list.forEach(i -> logger.info(new StringBuilder().append("id: ")
			.append(i.getId()).append("; Category: ").append(i.getName())));
		
		assertFalse(list.isEmpty());
	}
	
}
