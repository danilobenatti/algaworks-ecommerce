package com.algaworks.ecommerce.util;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.algaworks.ecommerce.model.Product;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class StartPersistenceUnit {
	
	static Logger logger = Logger.getLogger("");
	
	public static void main(String[] args) {
		
		EntityManagerFactory entityManagerFactory = Persistence
				.createEntityManagerFactory("algaworks-ecommerce");
		EntityManager entityManager = entityManagerFactory
				.createEntityManager();
		
		Product product = entityManager.find(Product.class, 1L);
		logger.log(Level.INFO, "{0}", product.getDescription());
		
		entityManager.close();
		entityManagerFactory.close();
		
	}
}
