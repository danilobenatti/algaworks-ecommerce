package com.algaworks.ecommerce.concurrence;

import static org.awaitility.Awaitility.await;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.Duration;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.message.StringFormattedMessage;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.algaworks.ecommerce.model.Product;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.OptimisticLockException;
import jakarta.persistence.Persistence;

class LockOptimistTest {
	
	protected static EntityManagerFactory entityManagerFactory;
	
	protected static Logger logger = LogManager.getLogger();
	
	@BeforeAll
	static void setUpBeforeClass() {
		entityManagerFactory = Persistence
			.createEntityManagerFactory("algaworks-ecommerce");
	}
	
	@AfterAll
	static void tearDownAfterClass() {
		entityManagerFactory.close();
	}
	
	private static void log(Object obj, Object... args) {
		logger.log(Level.INFO, new StringFormattedMessage("[%d] %s",
			System.currentTimeMillis(), obj.toString()));
	}
	
	private static void waiting(Duration seconds) {
		await().atMost(Duration.ofSeconds(10)).during(seconds)
			.until(() -> true);
	}
	
	@Test
	void usingLockOptimist() {
		Runnable runnable_1 = () -> {
			EntityManager em_1 = entityManagerFactory.createEntityManager();
			em_1.getTransaction().begin();
			
			Product product = em_1.find(Product.class, 1L);
			log("Runnable 1: charge product 1 by João");
			
			log("Runnable 1: waiting for 6 seconds");
			waiting(Duration.ofSeconds(6));
			
			log("Runnable 1: updating a product by João");
			product.setDescription("Mais memória, novas cores");
			
			em_1.getTransaction().commit();
			em_1.close();
			log("Runnable 1: confirm transaction by João");
		};
		
		Runnable runnable_2 = () -> {
			EntityManager em_2 = entityManagerFactory.createEntityManager();
			em_2.getTransaction().begin();
			
			Product product = em_2.find(Product.class, 1L);
			log("Runnable 2: charge product 1 by Maria");
			
			log("Runnable 2: waiting for 3 seconds");
			waiting(Duration.ofSeconds(3));
			
			log("Runnable 2: updating a product by Maria");
			product.setDescription("Novo Kindle, mais memória e novas cores!");
			
			em_2.getTransaction().commit();
			em_2.close();
			log("Runnable 2: confirm transaction by Maria");
		};
		
		Thread thread_1 = new Thread(runnable_1);
		Thread thread_2 = new Thread(runnable_2);
		
		thread_1.start();
		thread_2.start();
		
		try {
			thread_1.join();
			thread_2.join();
		} catch (OptimisticLockException | InterruptedException ex) {
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
