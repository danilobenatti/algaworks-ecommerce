package com.algaworks.ecommerce.jpql;

import static org.junit.jupiter.api.Assertions.assertFalse;

import java.util.List;

import org.junit.jupiter.api.Test;

import com.algaworks.ecommerce.EntityManagerTest;
import com.algaworks.ecommerce.model.Category;

import jakarta.persistence.TypedQuery;

class ResultsPaginationTest extends EntityManagerTest {
	
	@Test
	void pageResults() {
		// select * from tbl_products as p limit 2,2 -> MySQL
		// select * from tbl_products as p limit 3 -> return 3 products
		String jpql = "select c from Category c order by c.name";
		
		TypedQuery<Category> typedQuery = entityManager.createQuery(jpql,
			Category.class);
		// FIRST_RESULT = MAX_RESULTS * (page - 1)
		typedQuery.setFirstResult(0);
		typedQuery.setMaxResults(4);
		
		List<Category> resultList = typedQuery.getResultList();
		
		resultList.forEach(
			c -> logger.info(String.format("%d - %s", c.getId(), c.getName())));
		assertFalse(resultList.isEmpty());
	}
	
}
