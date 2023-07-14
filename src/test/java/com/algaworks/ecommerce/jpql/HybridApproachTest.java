package com.algaworks.ecommerce.jpql;

import static org.junit.jupiter.api.Assertions.assertFalse;

import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.algaworks.ecommerce.EntityManagerTest;
import com.algaworks.ecommerce.hibernate.SchemaTenantResolver;
import com.algaworks.ecommerce.model.Category;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;

class HybridApproachTest extends EntityManagerTest {
	
	@BeforeAll
	public static void setUpBeforeClass() {
		SchemaTenantResolver.setTenantIdentifier("algaworks_ecommerce");
		entityManagerFactory = Persistence
			.createEntityManagerFactory("algaworks-ecommerce");
		EntityManager em = entityManagerFactory.createEntityManager();
		
//		String value = System.getProperty("name.variable");
		
		String jpql = "select c from Category c";
		TypedQuery<Category> typedQuery = em.createQuery(jpql, Category.class);
//		typedQuery.setParameter("variable", value);
		
		entityManagerFactory.addNamedQuery("Category.list", typedQuery);
		
	}
	
	@Test
	void hybridApproach() {
		TypedQuery<Category> typedQuery = entityManager
			.createNamedQuery("Category.list", Category.class);
		
		List<Category> resultList = typedQuery.getResultList();
		
		assertFalse(resultList.isEmpty());
	}
	
}
