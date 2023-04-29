package com.algaworks.ecommerce.jpql;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import com.algaworks.ecommerce.EntityManagerTest;

import jakarta.persistence.TypedQuery;

class CondicionalExpressionsTest extends EntityManagerTest {
	
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
	
}
