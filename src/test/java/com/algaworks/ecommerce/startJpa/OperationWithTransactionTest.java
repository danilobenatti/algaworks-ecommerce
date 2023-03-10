package com.algaworks.ecommerce.startJpa;

import java.math.BigDecimal;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.algaworks.ecommerce.EntityManagerTest;
import com.algaworks.ecommerce.model.Product;

class OperationWithTransactionTest extends EntityManagerTest {
	
	@Test
	void preventOperationWithDatabase() {
		Product p = entityManager.find(Product.class, 1);
		entityManager.detach(p);
		
		entityManager.getTransaction().begin();
		p.setName("Kindle Paperwhite 2ª Geração");
		entityManager.getTransaction().commit();
		
		entityManager.clear();
		
		Product updatedProduct = entityManager.find(Product.class, p.getId());
		Assertions.assertEquals("Kindle Paperwhite", updatedProduct.getName());
	}
	
	@Test
	void showDiferenceBetweenPersistAndMerge() {
		Product pp = new Product();
//		p.setId(5);
		pp.setName("Smartphone One Plus");
		pp.setDescription("Tela e processador de última geração.");
		pp.setUnitPrice(BigDecimal.valueOf(1750.87));
		
		entityManager.getTransaction().begin();
		entityManager.persist(pp);
		pp.setName("Smartphone One Gold Edition");
		entityManager.getTransaction().commit();
		
		entityManager.clear();
		
		Product persistProduct = entityManager.find(Product.class, pp.getId());
		Assertions.assertNotNull(persistProduct);
		
		Product pm = new Product();
//		p.setId(6);
		pm.setName("Notebook Dell");
		pm.setDescription("Inspiron 5000 Series");
		pm.setUnitPrice(BigDecimal.valueOf(5500.00));
		
		entityManager.getTransaction().begin();
		pm = entityManager.merge(pm);
		pm.setName("Notebook Dell 5530 i7 3.8Ghz");
		entityManager.getTransaction().commit();
		
		entityManager.clear();
		
		Product mergeProduct = entityManager.find(Product.class, pm.getId());
		Assertions.assertNotNull(mergeProduct);
	}
	
	@Test
	void insertObjectWithMerge() {
		Product p = new Product();
		p.setName("Microfone Rode Videomic Go");
		p.setDescription("Qualidade insuperável.");
		p.setUnitPrice(BigDecimal.valueOf(850.24));
		
		entityManager.getTransaction().begin();
		Product p1 = entityManager.merge(p);
		entityManager.getTransaction().commit();
		
		entityManager.clear();
		
		Product insertedProduct = entityManager.find(Product.class, p1.getId());
		Assertions.assertNotNull(insertedProduct);
	}
	
	@Test
	void updateObjectManaged() {
		Product p = entityManager.find(Product.class, 1);
		p.setName("Kindle Paperwhite 2ª Geração");
		
		entityManager.getTransaction().begin();
		p.setUnitPrice(BigDecimal.valueOf(600.99));
		entityManager.getTransaction().commit();
		
		entityManager.clear();
		
		Assertions.assertEquals("Kindle Paperwhite 2ª Geração", p.getName());
		Assertions.assertEquals(BigDecimal.valueOf(600.99), p.getUnitPrice());
	}
	
	@Test
	void updateObject() {
		Product p = new Product();
		p.setId(1L);
		p.setName("Kindle Paperwhite");
		p.setDescription("Conheça o novo Kindle!");
		p.setUnitPrice(new BigDecimal(599.99));
		
		entityManager.getTransaction().begin();
		entityManager.merge(p);
		entityManager.getTransaction().commit();
		
		entityManager.clear();
		
		Product pUp = entityManager.find(Product.class, p.getId());
		
		Assertions.assertNotNull(pUp);
		Assertions.assertEquals("Kindle Paperwhite", pUp.getName());
		Assertions.assertEquals("Conheça o novo Kindle!", pUp.getDescription());
		Assertions.assertEquals(BigDecimal.valueOf(599.99), pUp.getUnitPrice());
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
		Product product = new Product();
		product.setName("Câmera Canon");
		product.setDescription("A melhor definição para suas fotos");
		product.setUnitPrice(BigDecimal.valueOf(5500.65));
		
		entityManager.getTransaction().begin();
		entityManager.persist(product);
		entityManager.getTransaction().commit();
		
		entityManager.clear();
		
		Product insertedProduct = entityManager.find(Product.class,
				product.getId());
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
