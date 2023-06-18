package com.algaworks.ecommerce.concurrence;

import static org.awaitility.Awaitility.await;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.Duration;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.message.StringFormattedMessage;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import com.algaworks.ecommerce.model.Product;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.LockModeType;
import jakarta.persistence.Persistence;

class LockPessimisticTest {
	
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
	
	private static void waiting(Long seconds, Long limit) {
		await().atMost(Duration.ofSeconds(limit))
			.during(Duration.ofSeconds(seconds)).until(() -> true);
	}
	
	private static final Long timeout = 10L;
	
	private static final LockModeType PESSIMISTIC_READ = LockModeType.PESSIMISTIC_READ;
	
	@ParameterizedTest
	@ValueSource(chars = { 'A', 'B', 'C', 'D' })
	void usingLockPessimisticLockModeTypePessimisticRead(char lockScene) {
		
		Runnable runnable_1 = () -> {
			log("Start Runnable 1");
			
			String newDescription = new StringBuilder()
				.append("Descrição detalhada. CTM: ")
				.append(System.currentTimeMillis()).toString();
			
			EntityManager em = entityManagerFactory.createEntityManager();
			em.getTransaction().begin();
			
			log("Load Product by Runnable 1");
			Product product = switch (lockScene) {
				case 'A' -> em.find(Product.class, 1L);
				case 'B' -> em.find(Product.class, 1L, PESSIMISTIC_READ);
				case 'C' -> em.find(Product.class, 1L);
				case 'D' -> em.find(Product.class, 1L, PESSIMISTIC_READ);
				default -> null;
			};
			
			log("Change Product by Runnable 1");
			product.setDescription(newDescription);
			
			log("Wait 4 seconds - Runnable 1");
			waiting(4L, timeout);
			
			log("Transaction confirmation by Runnable 1");
			em.getTransaction().commit();
			
			log("Ending Runnable 1");
			em.close();
		};
		
		Runnable runnable_2 = () -> {
			log("Start Runnable 2");
			
			String newDescription = new StringBuilder()
				.append("Descrição completa. CTM: ")
				.append(System.currentTimeMillis()).toString();
			
			EntityManager em = entityManagerFactory.createEntityManager();
			em.getTransaction().begin();
			
			log("Load Product by Runnable 2");
			Product product = switch (lockScene) {
				case 'A' -> em.find(Product.class, 1L);
				case 'B' -> em.find(Product.class, 1L);
				case 'C' -> em.find(Product.class, 1L, PESSIMISTIC_READ);
				case 'D' -> em.find(Product.class, 1L, PESSIMISTIC_READ);
				default -> null;
			};
			
			log("Change Product by Runnable 2");
			product.setDescription(newDescription);
			
			log("Wait 2 seconds - Runnable 2");
			waiting(2L, timeout);
			
			log("Transaction confirmation by Runnable 2");
			em.getTransaction().commit();
			
			log("Ending Runnable 2");
			em.close();
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
		
		EntityManager em = entityManagerFactory.createEntityManager();
		Product product = em.find(Product.class, 1L);
		em.close();
		
		switch (lockScene) {
			case 'A' -> assertTrue(
				product.getDescription().startsWith("Descrição completa"));
			case 'B' -> assertTrue(
				product.getDescription().startsWith("Descrição detalhada"));
			case 'C' -> assertTrue(
				product.getDescription().startsWith("Descrição completa"));
			case 'D' -> assertTrue(
				product.getDescription().startsWith("Descrição completa"));
			default -> assertTrue(false);
		}
		log("Finalizing test method");
	}
	
}
