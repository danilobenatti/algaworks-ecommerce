package com.algaworks.ecommerce.startJpa;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.algaworks.ecommerce.model.Product;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class QueryingRegistryTest {
	
	private static EntityManagerFactory entityManagerFactory;
	
	private static EntityManager entityManager;
	
	@BeforeClass
	public static void setUpBeforeClass() {
		entityManagerFactory = Persistence
				.createEntityManagerFactory("algaworks-ecommerce");
	}
	
	@AfterClass
	public static void tearDownAfterClass() {
		entityManagerFactory.close();
	}
	
	@Before
	public void setUp() {
		entityManager = entityManagerFactory.createEntityManager();
	}
	
	@After
	public void setDown() {
		entityManager.close();
	}
	
	@Test
	public void searchById() {
		Product product = entityManager.find(Product.class, 1);
//		Product product = entityManager.getReference(Product.class, 1);
		Assert.assertNotNull(product);
		Assert.assertEquals("Kindle", product.getName());
	}
	
	@Test
	public void updateReference() {
		Product product = entityManager.find(Product.class, 1);
		product.setName("AirPad");
		entityManager.refresh(product);
		Assert.assertEquals("Kindle", product.getName());
	}
	
}
