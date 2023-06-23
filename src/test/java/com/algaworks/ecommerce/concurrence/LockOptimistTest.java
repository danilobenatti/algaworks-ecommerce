package com.algaworks.ecommerce.concurrence;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import com.algaworks.ecommerce.EntityManagerTest;
import com.algaworks.ecommerce.hibernate.SchemaTenantResolver;
import com.algaworks.ecommerce.model.Product;

import jakarta.persistence.EntityManager;

class LockOptimistTest extends EntityManagerTest{
	
	private static final Long timeout = 10L;
	
	@Test
	void usingLockOptimist() {
		Runnable runnable_1 = () -> {
			SchemaTenantResolver.setTenantIdentifier("algaworks_ecommerce");
			EntityManager em_1 = entityManagerFactory.createEntityManager();
			em_1.getTransaction().begin();
			
			Product product = em_1.find(Product.class, 1L);
			log("Runnable 1: charge product 1 by João");
			
			log("Runnable 1: waiting for 6 seconds");
			waiting(6L, timeout);
			
			log("Runnable 1: updating a product by João");
			product.setDescription("Mais memória, novas cores");
			
			em_1.getTransaction().commit();
			em_1.close();
			log("Runnable 1: confirm transaction by João");
		};
		
		Runnable runnable_2 = () -> {
			SchemaTenantResolver.setTenantIdentifier("algaworks_ecommerce");
			EntityManager em_2 = entityManagerFactory.createEntityManager();
			em_2.getTransaction().begin();
			
			Product product = em_2.find(Product.class, 1L);
			log("Runnable 2: charge product 1 by Maria");
			
			log("Runnable 2: waiting for 3 seconds");
			waiting(3L, timeout);
			
			log("Runnable 2: updating a product by Maria");
			product.setDescription("Novo Kindle, mais memória e novas cores!");
			
			em_2.getTransaction().commit();
			em_2.close();
			log("Runnable 2: confirm transaction by Maria");
		};
		
		Thread thread_1 = new Thread(runnable_1);
		Thread thread_2 = new Thread(runnable_2);
		
		thread_1.start();
		waiting(1L, timeout);
		thread_2.start();
		
		try {
			thread_1.join();
			thread_2.join();
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
		
		EntityManager em_3 = entityManagerFactory.createEntityManager();
		Product product = em_3.find(Product.class, 1L);
		em_3.close();
		
		assertEquals("Novo Kindle, mais memória e novas cores!",
			product.getDescription());
		log("Finalizing test method");
	}
	
}
