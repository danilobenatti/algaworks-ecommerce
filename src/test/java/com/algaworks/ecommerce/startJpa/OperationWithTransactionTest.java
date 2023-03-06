package com.algaworks.ecommerce.startJpa;

import java.math.BigDecimal;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.algaworks.ecommerce.EntityManagerTest;
import com.algaworks.ecommerce.model.Product;

class OperationWithTransactionTest extends EntityManagerTest {
	
	@Test
	void insertObjectWithMerge() {
		Product p = new Product();
		p.setId(4);
		p.setName("Microfone Rode Videomic Go");
		p.setDescription("Qualidade insuperável.");
		p.setPrice(BigDecimal.valueOf(850.24));
		
		entityManager.getTransaction().begin();
		entityManager.merge(p);
		entityManager.getTransaction().commit();
		
		entityManager.clear();
		
		Product insertedProduct = entityManager.find(Product.class, p.getId());
		Assertions.assertNotNull(insertedProduct);
	}
	
	@Test
	void updateObjectManaged() {
		Product p = entityManager.find(Product.class, 1);
		p.setName("Kindle Paperwhite 2ª Geração");
		
		entityManager.getTransaction().begin();
		p.setPrice(BigDecimal.valueOf(600.99));
		entityManager.getTransaction().commit();
		
		entityManager.clear();
		
		Assertions.assertEquals("Kindle Paperwhite 2ª Geração", p.getName());
		Assertions.assertEquals(BigDecimal.valueOf(600.99), p.getPrice());
	}
	
	@Test
	void updateObject() {
		Product p = new Product();
		p.setId(1);
		p.setName("Kindle Paperwhite");
		p.setDescription("Conheça o novo Kindle!");
		p.setPrice(new BigDecimal(599.99));
		
		entityManager.getTransaction().begin();
		entityManager.merge(p);
		entityManager.getTransaction().commit();
		
		entityManager.clear();
		
		Product pUp = entityManager.find(Product.class, p.getId());
		
		Assertions.assertNotNull(pUp);
		Assertions.assertEquals("Kindle Paperwhite", pUp.getName());
		Assertions.assertEquals("Conheça o novo Kindle!", pUp.getDescription());
		Assertions.assertEquals(BigDecimal.valueOf(599.99), pUp.getPrice());
	}
	
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
		Assertions.assertNull(null);
	}
	
}
