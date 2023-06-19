package com.algaworks.ecommerce;

import static org.awaitility.Awaitility.await;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.config.Configurator;
import org.apache.logging.log4j.message.StringFormattedMessage;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class EntityManagerFactoryTest {
	
	protected static EntityManagerFactory entityManagerFactory;
	
	protected static Logger logger = LogManager.getLogger();
	
	@BeforeAll
	public static void setUpBeforeClass() {
		
		Map<String, String> env = System.getenv();
		HashMap<Object, Object> configOverrides = new HashMap<>();
		for (String envName : env.keySet()) {
			if (envName.contains("DB_USERNAME")) {
				configOverrides.put("jakarta.persistence.jdbc.user",
					env.get(envName));
			}
			if (envName.contains("DB_PASSWORD")) {
				configOverrides.put("jakarta.persistence.jdbc.password",
					env.get(envName));
			}
		}
		entityManagerFactory = Persistence
			.createEntityManagerFactory("algaworks-ecommerce", configOverrides);
		
		Configurator.initialize(EntityManagerTest.class.getName(),
			"./src/main/resources/log4j2.properties");
	}
	
	@AfterAll
	public static void tearDownAfterClass() {
		entityManagerFactory.close();
	}
	
	public static void log(Object obj, Object... args) {
		logger.log(Level.INFO, new StringFormattedMessage("[%d] %s",
			System.currentTimeMillis(), obj.toString(), args));
	}
	
	public static void waiting(Long seconds, Long limit) {
		await().atMost(Duration.ofSeconds(limit))
			.during(Duration.ofSeconds(seconds)).until(() -> true);
	}
	
}
