package com.algaworks.ecommerce.jpql;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.algaworks.ecommerce.EntityManagerTest;
import com.algaworks.ecommerce.model.Order;
import com.algaworks.ecommerce.model.Product;

import jakarta.persistence.TypedQuery;

class ConditionalExpressionsTest extends EntityManagerTest {
	
	@Test
	void usingCondicionalExpressionLike() {
		String jpql = "select p from Person p where p.firstname like concat('%', :name, '%')";
		
		TypedQuery<Object[]> typedQuery = entityManager.createQuery(jpql,
			Object[].class);
		typedQuery.setParameter("name", "m");
		
		List<Object[]> resultList = typedQuery.getResultList();
		assertFalse(resultList.isEmpty());
		
	}
	
	@Test
	void usingIsNotNull() {
		String jpql = "select p from Product p where p.image is not null";
		
		TypedQuery<Object[]> typedQuery = entityManager.createQuery(jpql,
			Object[].class);
		
		List<Object[]> resultList = typedQuery.getResultList();
		assertTrue(resultList.isEmpty());
		
	}
	
	void exec(String str) {
		String jpql = str;
		
		TypedQuery<Object[]> typedQuery = entityManager.createQuery(jpql,
			Object[].class);
		
		List<Object[]> resultList = typedQuery.getResultList();
		assertFalse(resultList.isEmpty());
	}
	
	@Test
	void usingIsNull() {
		exec("select p from Product p where p.image is null");
	}
	
	@Test
	void usingIsEmpty() {
		/**
		 * select * from tbl_products as p left join tbl_product_category as pc
		 * on p.id = pc.product_id where category_id is null;
		 */
		exec("select p from Product p where p.categories is empty");
	}
	
	@Test
	void usingIsNotEmpty() {
		/**
		 * select * from tbl_products as p join tbl_product_category as pc on
		 * p.id = pc.product_id where category_id is not null or category_id =
		 * '' order by id;
		 */
		exec(
			"select p from Product p where p.categories is not empty order by p.id");
	}
	
	@Test
	void usingGreaterMinor() {
		String jpql = "select p from Product p where p.unitPrice >= :start and p.unitPrice <= :end";
		
		TypedQuery<Product> typedQuery = entityManager.createQuery(jpql,
			Product.class);
		typedQuery.setParameter("start", BigDecimal.valueOf(499.50));
		typedQuery.setParameter("end", BigDecimal.valueOf(1600.00));
		
		List<Product> resultList = typedQuery.getResultList();
		assertFalse(resultList.isEmpty());
		
	}
	
	@Test
	void usingGreaterMinorWithDate() {
		String jpql = "select o from Order o where o.dateCreate >= :date";
		
		TypedQuery<Order> typedQuery = entityManager.createQuery(jpql,
			Order.class);
		typedQuery.setParameter("date", LocalDateTime.now().minusYears(1));
		
		List<Order> resultList = typedQuery.getResultList();
		assertFalse(resultList.isEmpty());
		
	}
	
	@Test
	void usingBetweenProduct() {
		String jpql = "select p from Product p where p.unitPrice between :start and :end";
		
		TypedQuery<Product> typedQuery = entityManager.createQuery(jpql,
			Product.class);
		typedQuery.setParameter("start", BigDecimal.valueOf(499.50));
		typedQuery.setParameter("end", BigDecimal.valueOf(1506.72));
		
		List<Product> resultList = typedQuery.getResultList();
		assertFalse(resultList.isEmpty());
		
	}
	
	@Test
	void usingBetweenOrders() {
		String jpql = "select o from Order o where o.dateCreate between :start and :end";
		
		TypedQuery<Order> typedQuery = entityManager.createQuery(jpql,
			Order.class);
		typedQuery.setParameter("start", LocalDateTime.now().minusYears(1));
		typedQuery.setParameter("end", LocalDateTime.now());
		
		List<Order> resultList = typedQuery.getResultList();
		assertFalse(resultList.isEmpty());
		
	}
	
	@Test
	void usingDifferentExpression() {
		String jpql = "select p from Product p where p.unit <> 1";
		
		TypedQuery<Product> typedQuery = entityManager.createQuery(jpql,
			Product.class);
		List<Product> list = typedQuery.getResultList();
		
		assertFalse(list.isEmpty());
		
	}
}
