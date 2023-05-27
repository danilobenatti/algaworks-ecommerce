package com.algaworks.ecommerce.criteria;

import static org.junit.jupiter.api.Assertions.assertFalse;

import java.util.List;

import org.junit.jupiter.api.Test;

import com.algaworks.ecommerce.EntityManagerTest;
import com.algaworks.ecommerce.model.Category;
import com.algaworks.ecommerce.model.Category_;

import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

class ResultsPaginationCriteriaTest extends EntityManagerTest {
	
	@Test
	void pageResults() {
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Category> query = builder.createQuery(Category.class);
		Root<Category> root = query.from(Category.class);
		/*
		 * JPQL = "select c from Category c order by c.name"
		 */
		query.select(root);
		query.orderBy(builder.asc(root.get(Category_.name)));
		
		TypedQuery<Category> typedQuery = entityManager.createQuery(query);
		// FIRST_RESULT = MAX_RESULTS * (page - 1)
		typedQuery.setFirstResult(0);
		typedQuery.setMaxResults(4);
		
		List<Category> resultList = typedQuery.getResultList();
		
		resultList.forEach(
			c -> logger.info(String.format("%d - %s", c.getId(), c.getName())));
		assertFalse(resultList.isEmpty());
	}
	
}
