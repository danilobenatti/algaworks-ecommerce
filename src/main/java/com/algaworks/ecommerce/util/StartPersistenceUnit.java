package com.algaworks.ecommerce.util;

import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.config.Configurator;

import com.algaworks.ecommerce.model.Product;
import com.algaworks.ecommerce.service.InvoiceService;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class StartPersistenceUnit {
	
	protected static Logger logger = LogManager.getLogger();
	
	public static void main(String[] args) {
		
		Configurator.initialize(InvoiceService.class.getName(),
			"./src/main/resources/log4j2.properties");
		
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
