package com.algaworks.ecommerce.knowentitymanager;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

import com.algaworks.ecommerce.EntityManagerTest;
import com.algaworks.ecommerce.model.Product;

class FirstCacheLevelTest extends EntityManagerTest {
	
	@Test
	void checkCache() {
		Product product = entityManager.find(Product.class, 1L);
		System.out.println(product.getName());
		
		System.out.println("-----#-----");
		
//		entityManager.clear();
//		entityManager.close();
//		entityManager = entityManagerFactory.createEntityManager();
		
		Product product2 = entityManager.find(Product.class, product.getId());
		System.out.println(product2.getName());
		
		assertNotNull(product);
		assertNotNull(product2);
		
	}
	
}
