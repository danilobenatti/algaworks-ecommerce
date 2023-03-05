package com.algaworks.ecommerce.startJpa;

import java.math.BigDecimal;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.algaworks.ecommerce.EntityManagerTest;
import com.algaworks.ecommerce.model.Product;

class OperationWithTransactionTest extends EntityManagerTest {
	
	@Test
	void InsertFirstObject() {
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
	void OpenAndCloseTransaction() {
//		Product product = new Product();
		
		entityManager.getTransaction().begin();
		
//		entityManager.persist(product);
//		entityManager.merge(product);
//		entityManager.remove(product);
		
		entityManager.getTransaction().commit();
		
	}
	
}
