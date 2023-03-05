package com.algaworks.ecommerce.util;

import com.algaworks.ecommerce.model.Product;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class StartPersistenceUnit {
	
	public static void main(String[] args) {
		
		EntityManagerFactory entityManagerFactory = Persistence
				.createEntityManagerFactory("algaworks-ecommerce");
		EntityManager entityManager = entityManagerFactory
				.createEntityManager();
		
		Product product = entityManager.find(Product.class, 1);
		System.out.println(product);
		
		entityManager.close();
		entityManagerFactory.close();
		
	}
}
