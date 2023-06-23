package com.algaworks.ecommerce.concurrence;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import com.algaworks.ecommerce.EntityManagerTest;
import com.algaworks.ecommerce.hibernate.SchemaTenantResolver;
import com.algaworks.ecommerce.model.Product;

import jakarta.persistence.EntityManager;
import jakarta.persistence.LockModeType;

class LockPessimisticTest extends EntityManagerTest {
	
	private static final Long timeout = 10L;
	
	private static final LockModeType PESSIMISTIC_READ = LockModeType.PESSIMISTIC_READ;
	private static final LockModeType PESSIMISTIC_WRITE = LockModeType.PESSIMISTIC_WRITE;
	
	@ParameterizedTest
	@ValueSource(chars = { 'A', 'B', 'C', 'D' })
	void usingLockPessimisticLockModeTypePessimisticRead(char lockScene) {
		
		Runnable runnable_1 = () -> {
			log("Start Runnable 1");
			
			String newDescription = new StringBuilder()
				.append("Descrição detalhada. CTM: ")
				.append(System.currentTimeMillis()).toString();
			
			SchemaTenantResolver.setTenantIdentifier("algaworks_ecommerce");
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
			
			SchemaTenantResolver.setTenantIdentifier("algaworks_ecommerce");
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
	
	@ParameterizedTest
	@ValueSource(chars = { 'A', 'B', 'C', 'D' })
	void usingLockPessimisticLockModeTypePessimisticWrite(char lockScene) {
		
		Runnable runnable_1 = () -> {
			log("Start Runnable 1");
			
			String newDescription = new StringBuilder()
				.append("Descrição detalhada. CTM: ")
				.append(System.currentTimeMillis()).toString();
			
			SchemaTenantResolver.setTenantIdentifier("algaworks_ecommerce");
			EntityManager em = entityManagerFactory.createEntityManager();
			em.getTransaction().begin();
			
			log("Load Product by Runnable 1");
			Product product = switch (lockScene) {
				case 'A' -> em.find(Product.class, 1L);
				case 'B' -> em.find(Product.class, 1L, PESSIMISTIC_WRITE);
				case 'C' -> em.find(Product.class, 1L);
				case 'D' -> em.find(Product.class, 1L, PESSIMISTIC_WRITE);
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
			
			SchemaTenantResolver.setTenantIdentifier("algaworks_ecommerce");
			EntityManager em = entityManagerFactory.createEntityManager();
			em.getTransaction().begin();
			
			log("Load Product by Runnable 2");
			Product product = switch (lockScene) {
				case 'A' -> em.find(Product.class, 1L);
				case 'B' -> em.find(Product.class, 1L);
				case 'C' -> em.find(Product.class, 1L, PESSIMISTIC_WRITE);
				case 'D' -> em.find(Product.class, 1L, PESSIMISTIC_WRITE);
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
	
	@SuppressWarnings("unchecked")
	@Test
	void usingLockNativeTypeQuery() {
		Runnable runnable_1 = () -> {
			log("Start Runnable 1");
			
			String newDescription = new StringBuilder()
				.append("Descrição detalhada. CTM: ")
				.append(System.currentTimeMillis()).toString();
			
			SchemaTenantResolver.setTenantIdentifier("algaworks_ecommerce");
			EntityManager em = entityManagerFactory.createEntityManager();
			em.getTransaction().begin();
			
			log("Load Product by Runnable 1");
			List<Product> list = em.createQuery("select p from Product p")
				.setLockMode(PESSIMISTIC_READ).getResultList();
			
			Product product = list.stream().filter(p -> p.getId().equals(1L))
				.findFirst().get();
			
			log("Change Product by Runnable 1");
			product.setDescription(newDescription);
			
			log("Wait 3 seconds - Runnable 1");
			waiting(3L, timeout);
			
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
			
			SchemaTenantResolver.setTenantIdentifier("algaworks_ecommerce");
			EntityManager em = entityManagerFactory.createEntityManager();
			em.getTransaction().begin();
			
			log("Load Product by Runnable 2");
			Product product = em.find(Product.class, 1L, PESSIMISTIC_WRITE);
			
			log("Change Product by Runnable 2");
			product.setDescription(newDescription);
			
			log("Wait 1 seconds - Runnable 2");
			waiting(1L, timeout);
			
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
		
		assertTrue(product.getDescription().startsWith("Descrição completa"));
	}
	
}
