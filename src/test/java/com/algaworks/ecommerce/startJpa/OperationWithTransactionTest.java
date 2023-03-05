package com.algaworks.ecommerce.startJpa;

import java.math.BigDecimal;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.algaworks.ecommerce.EntityManagerTest;
import com.algaworks.ecommerce.model.Product;

class OperationWithTransactionTest extends EntityManagerTest {
	
	@Test
	void removeObject() {
		Product product = entityManager.find(Product.class, 3);
		
		entityManager.getTransaction().begin();
		entityManager.remove(product);
		entityManager.getTransaction().commit();
		
		Product productRemoved = entityManager.find(Product.class, 3);
		Assertions.assertNull(productRemoved);
	}
	
	@Test
	void insertFirstObject() {
		Product product = new Product(null, "Câmera Canon",
				"A melhor definição para suas fotos", new BigDecimal(5500.65));
		
		entityManager.getTransaction().begin();
		entityManager.persist(product);
		entityManager.getTransaction().commit();
		
		entityManager.clear();
		
		Product insertedProduct = entityManager.find(Product.class,
				product.getId());
		System.out.println(insertedProduct);
		Assertions.assertNotNull(insertedProduct);
	}
	
	@Test
	void openAndCloseTransaction() {
//		Product product = new Product();
		
		entityManager.getTransaction().begin();
		
//		entityManager.persist(product);
//		entityManager.merge(product);
//		entityManager.remove(product);
		
		entityManager.getTransaction().commit();
		
	}
	
}
