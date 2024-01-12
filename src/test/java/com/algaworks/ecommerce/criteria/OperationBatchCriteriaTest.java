package com.algaworks.ecommerce.criteria;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;

import com.algaworks.ecommerce.EntityManagerTest;
import com.algaworks.ecommerce.model.Category;
import com.algaworks.ecommerce.model.Category_;
import com.algaworks.ecommerce.model.Product;
import com.algaworks.ecommerce.model.Product_;

import jakarta.persistence.Query;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaDelete;
import jakarta.persistence.criteria.CriteriaUpdate;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.criteria.Subquery;

class OperationBatchCriteriaTest extends EntityManagerTest {
	
	@Test
	void batchUpdateCriteria() {
		/*
		 * JPQL = update Product p1 set p1.unitPrice = p1.unitPrice * 0.95 where
		 * exists (select 1 from p1.categories c2 where c2.id = 4)
		 * 
		 * addition: sum(a, b) substraction: diff(a, b) multiplication: prod(a,
		 * b) division: quot(a, b)
		 */
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaUpdate<Product> criteriaUpdate = criteriaBuilder
			.createCriteriaUpdate(Product.class);
		Root<Product> root = criteriaUpdate.from(Product.class);
		
		criteriaUpdate.set(root.get(Product_.unitPrice), criteriaBuilder
			.prod(root.get(Product_.unitPrice), BigDecimal.valueOf(0.95)));
		
		Subquery<Integer> subquery = criteriaUpdate.subquery(Integer.class);
		Root<Product> subqueryRoot = subquery.correlate(root);
		Join<Product, Category> joinCategory = subqueryRoot
			.join(Product_.categories);
		subquery.select(criteriaBuilder.literal(1));
		subquery
			.where(criteriaBuilder.equal(joinCategory.get(Category_.id), 4L));
		
		criteriaUpdate.where(criteriaBuilder.exists(subquery));
		
		Query query = entityManager.createQuery(criteriaUpdate);
		
		entityManager.getTransaction().begin();
		query.executeUpdate();
		entityManager.getTransaction().commit();
		
		Product p = entityManager.find(Product.class, 1L);
		assertEquals(BigDecimal.valueOf(759.53), p.getUnitPrice());
	}
	
	@Test
	void batchRemoveCriteria() {
		/*
		 * JPQL = delete from Product p where p.id between 7 and 16
		 */
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaDelete<Product> criteriaDelete = criteriaBuilder
			.createCriteriaDelete(Product.class);
		Root<Product> root = criteriaDelete.from(Product.class);
		
		criteriaDelete
			.where(criteriaBuilder.between(root.get(Product_.id), 7L, 16L));
		
		Query query = entityManager.createQuery(criteriaDelete);
		
		entityManager.getTransaction().begin();
		query.executeUpdate();
		entityManager.getTransaction().commit();
		
		for (int i = 7; i <= 16; i++) {
			assertNull(entityManager.find(Product.class, i));
		}
	}
}
