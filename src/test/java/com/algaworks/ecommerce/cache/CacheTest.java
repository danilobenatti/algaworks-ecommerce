package com.algaworks.ecommerce.cache;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import com.algaworks.ecommerce.EntityManagerFactoryTest;
import com.algaworks.ecommerce.model.Order;

import jakarta.persistence.Cache;
import jakarta.persistence.EntityManager;

class CacheTest extends EntityManagerFactoryTest {
	
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
	
	@Test
	void addOrderInCache() {
		EntityManager entityManager1 = entityManagerFactory
			.createEntityManager();
		EntityManager entityManager2 = entityManagerFactory
			.createEntityManager();
		
		entityManager1.createQuery("select o from Order o", Order.class)
			.getResultList();
		
		Order order1 = entityManager2.find(Order.class, 1L);
		logger.info(String.format("Search by instance 1: %d", order1.getId()));
		assertNotNull(order1);
	}
	
	@Test
	void containsOrderInCache() {
		Cache cache = entityManagerFactory.getCache();
		
		EntityManager entityManager = entityManagerFactory
			.createEntityManager();
		
		entityManager.createQuery("select o from Order o", Order.class)
			.getResultList();
		
		Order order = entityManager.find(Order.class, 1L);
		logger.info(String.format("Search by instance 1: %d", order.getId()));
		assertTrue(cache.contains(Order.class, order.getId()));
	}
	
	@ParameterizedTest
	@ValueSource(ints = { 1, 2, 3 })
	void removeOrderInCache(int i) {
		Cache cache = entityManagerFactory.getCache();
		
		EntityManager entityManager1 = entityManagerFactory
			.createEntityManager();
		EntityManager entityManager2 = entityManagerFactory
			.createEntityManager();
		
		entityManager1.createQuery("select o from Order o", Order.class)
			.getResultList();
		
		logger.info(String.format("Remove instance n°1 from cache"));
		switch (i) {
			case 1:
				cache.evict(Order.class, 1L);
				break;
			case 2:
				cache.evict(Order.class);
				break;
			case 3:
				cache.evictAll();
				break;
			default:
				throw new IllegalArgumentException("Unexpected value: " + i);
		}
		
		logger.info(String.format("Search by instance 1"));
		Order order1 = entityManager2.find(Order.class, 1L);
		assertNotNull(order1);
		logger.info(String.format("Search by instance 2"));
		Order order2 = entityManager2.find(Order.class, 3L);
		assertNotNull(order2);
	}
	
	@Test
	void analyzingCachingOptions() {
		Cache cache = entityManagerFactory.getCache();
		
		EntityManager entityManager = entityManagerFactory
			.createEntityManager();
		
		entityManager.createQuery("select o from Order o", Order.class)
			.getResultList();
		
		assertTrue(cache.contains(Order.class, 1L));
	}
}
