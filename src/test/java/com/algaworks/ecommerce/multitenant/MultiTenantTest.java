package com.algaworks.ecommerce.multitenant;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import com.algaworks.ecommerce.EntityManagerFactoryTest;
import com.algaworks.ecommerce.hibernate.SchemaTenantResolver;
import com.algaworks.ecommerce.model.Product;

import jakarta.persistence.EntityManager;

class MultiTenantTest extends EntityManagerFactoryTest {
	
	@Test
	void usingStrategyBySchema() {
		SchemaTenantResolver.setTenantIdentifier("algaworks_ecommerce");
		EntityManager entityManager1 = entityManagerFactory
			.createEntityManager();
		Product product1 = entityManager1.find(Product.class, 1L);
		assertEquals("Kindle", product1.getName());
		logger.info(new StringBuilder().append("Schema: algaworks_ecommerce, ")
			.append("Product: ").append(product1.getName()));
		entityManager1.close();
		
		SchemaTenantResolver.setTenantIdentifier("ecosensor_ecommerce");
		EntityManager entityManager2 = entityManagerFactory
			.createEntityManager();
		Product product2 = entityManager2.find(Product.class, 1L);
		assertEquals("Kindle Paperwhite", product2.getName());
		logger.info(new StringBuilder().append("Schema: ecosensor_ecommerce, ")
			.append("Product: ").append(product2.getName()));
		entityManager2.close();
		
	}
}
