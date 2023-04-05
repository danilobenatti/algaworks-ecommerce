package com.algaworks.ecommerce.util;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.algaworks.ecommerce.model.Product;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class StartPersistenceUnit {
	
	static Logger logger = Logger.getLogger("");
	
	public static void main(String[] args) {
		
		Map<String, String> env = System.getenv();
		HashMap<Object, Object> configOverrides = new HashMap<>();
		for (Map.Entry<String, String> entry : env.entrySet()) {
			if (entry.getKey().equals("DB_USERNAME")) {
				configOverrides.put("jakarta.persistence.jdbc.user",
					entry.getValue());
			}
			if (entry.getKey().equals("DB_PASSWORD")) {
				configOverrides.put("jakarta.persistence.jdbc.password",
					entry.getValue());
			}
		}
		
		EntityManagerFactory entityManagerFactory = Persistence
			.createEntityManagerFactory("algaworks-ecommerce", configOverrides);
		EntityManager entityManager = entityManagerFactory
			.createEntityManager();
		
		Product product = entityManager.find(Product.class, 1L);
		logger.log(Level.INFO, "{0}", product.getDescription());
		
		entityManager.close();
		entityManagerFactory.close();
		
	}
}
