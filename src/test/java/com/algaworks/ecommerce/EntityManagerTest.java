package com.algaworks.ecommerce;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class EntityManagerTest {
	
	protected static EntityManagerFactory entityManagerFactory;
	
	protected static EntityManager entityManager;
	
	@BeforeAll
	public static void setUpBeforeClass() {
		
		Map<String, String> env = System.getenv();
		HashMap<Object, Object> configOverrides = new HashMap<>();
		for (String envName : env.keySet()) {
			if (envName.contains("DB_USERNAME")) {
				configOverrides.put("jakarta.persistence.jdbc.user",
					env.get(envName));
			}
			if (envName.contains("DB_PASSWORD")) {
				configOverrides.put("jakarta.persistence.jdbc.password",
					env.get(envName));
			}
		}
		entityManagerFactory = Persistence
			.createEntityManagerFactory("algaworks-ecommerce", configOverrides);
	}
	
	@AfterAll
	public static void tearDownAfterClass() {
		entityManagerFactory.close();
	}
	
	@BeforeEach
	public void setUp() {
		entityManager = entityManagerFactory.createEntityManager();
	}
	
	@AfterEach
	public void tearDown() {
		entityManager.close();
	}
	
}
