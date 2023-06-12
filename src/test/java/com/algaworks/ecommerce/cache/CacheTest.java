package com.algaworks.ecommerce.cache;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.config.Configurator;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.algaworks.ecommerce.EntityManagerTest;
import com.algaworks.ecommerce.model.Order;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class CacheTest {
	
	protected static EntityManagerFactory entityManagerFactory;
	
	protected static Logger logger = LogManager.getLogger();
	
	@BeforeAll
	public static void setUpBeforeClass() {
		entityManagerFactory = Persistence
			.createEntityManagerFactory("algaworks-ecommerce");
	}
	
	@AfterAll
	public static void tearDownAfterClass() {
		Configurator.initialize(EntityManagerTest.class.getName(),
			"./src/main/resources/log4j2.properties");
		entityManagerFactory.close();
	}
	
	@Test
	void searchInCache() {
		EntityManager entityManager1 = entityManagerFactory
			.createEntityManager();
		EntityManager entityManager2 = entityManagerFactory
			.createEntityManager();
		
		Order order1 = entityManager1.find(Order.class, 1L);
		logger.info(String.format("Search by instance 1: %d", order1.getId()));
		assertNotNull(order1);
		
		Order order2 = entityManager1.find(Order.class, 1L);
		logger.info(String.format("Search by instance 2: %d", order2.getId()));
		assertNotNull(order2);
		
		Order order3 = entityManager2.find(Order.class, 1L);
		logger.info(String.format("Search by instance 3: %d", order3.getId()));
		assertNotNull(order3);
	}
}
