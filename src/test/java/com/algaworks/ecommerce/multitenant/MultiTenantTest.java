package com.algaworks.ecommerce.multitenant;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashMap;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.config.Configurator;
import org.junit.jupiter.api.Test;

import com.algaworks.ecommerce.EntityManagerTest;
import com.algaworks.ecommerce.hibernate.SchemaTenantResolver;
import com.algaworks.ecommerce.model.Product;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

class MultiTenantTest {
	
	protected static Logger log = LogManager.getLogger();
	
	protected static EntityManagerFactory emf;
	
	private String getDatabaseName(EntityManager em) {
		String dbName = null;
		String url = (String) em.getEntityManagerFactory().getProperties()
			.get("hibernate.connection.url");
		if (url != null) {
			dbName = url.substring(url.lastIndexOf('/') + 1, url.indexOf('?'));
		}
		return dbName;
	}
	
	@Test
	void usingStrategyBySchema() {
		
		Configurator.initialize(MultiTenantTest.class.getName(),
			"./src/main/resources/log4j2.properties");
		
		HashMap<String, String> cfg = new HashMap<>();
		cfg.put("hibernate.multiTenancy", "SCHEMA");
		cfg.put("hibernate.multi_tenant_connection_provider",
			"com.algaworks.ecommerce.hibernate.SchemaMultiTenantProvider");
		
		emf = Persistence.createEntityManagerFactory("algaworks-ecommerce",
			cfg);
		
		SchemaTenantResolver.setTenantIdentifier("algaworks_ecommerce");
		EntityManager em1 = emf.createEntityManager();
		log.info(String.format("<<< %s >>>", getDatabaseName(em1)));
		Product p1 = em1.find(Product.class, 1L);
		assertEquals("Kindle", p1.getName());
		log.info(new StringBuilder().append("Schema: algaworks_ecommerce")
			.append(", Product: ").append(p1.getName()));
		em1.close();
		
		SchemaTenantResolver.setTenantIdentifier("ecosensor_ecommerce");
		EntityManager em2 = emf.createEntityManager();
		log.info(String.format("<<< %s >>>", getDatabaseName(em2)));
		Product p2 = em2.find(Product.class, 1L);
		assertEquals("Kindle Paperwhite", p2.getName());
		log.info(new StringBuilder().append("Schema: ecosensor_ecommerce")
			.append(", Product: ").append(p2.getName()));
		em2.close();
	}
	
	@Test
	void usingStrategyByMachine() {
		
		Configurator.initialize(EntityManagerTest.class.getName(),
			"./src/main/resources/log4j2.properties");
		
		HashMap<String, String> cfg = new HashMap<>();
		cfg.put("hibernate.multiTenancy", "DATABASE");
		cfg.put("hibernate.multi_tenant_connection_provider",
			"com.algaworks.ecommerce.hibernate.MachineMultiTenantProvider");
		
		emf = Persistence.createEntityManagerFactory("algaworks-ecommerce",
			cfg);
		
		SchemaTenantResolver.setTenantIdentifier("algaworks_ecommerce");
		EntityManager em1 = emf.createEntityManager();
		Product p1 = em1.find(Product.class, 1L);
		log.info(String.format("<<< %s >>>", getDatabaseName(em1)));
		assertEquals("Kindle", p1.getName());
		log.info(new StringBuilder().append("Database: algaworks_ecommerce, ")
			.append("Product: ").append(p1.getName()));
		em1.close();
		
		SchemaTenantResolver.setTenantIdentifier("ecosensor_ecommerce");
		EntityManager em2 = emf.createEntityManager();
		Product p2 = em2.find(Product.class, 1L);
		log.info(String.format("<<< %s >>>", getDatabaseName(em2)));
		assertEquals("Kindle Paperwhite", p2.getName());
		log.info(new StringBuilder().append("Database: ecosensor_ecommerce, ")
			.append("Product: ").append(p2.getName()));
		em2.close();
	}
	
}
