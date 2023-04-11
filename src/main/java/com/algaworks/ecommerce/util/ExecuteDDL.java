package com.algaworks.ecommerce.util;

import java.util.HashMap;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class ExecuteDDL {
	
	public static void main(String[] args) {
		
		HashMap<String, String> prop = new HashMap<>();
		
		prop.put("jakarta.persistence.schema-generation.database.action",
			"drop-and-create");
		
		prop.put("jakarta.persistence.schema-generation.create-source",
			"metadata-then-script");
		prop.put("jakarta.persistence.schema-generation.drop-source",
			"metadata-then-script");
		
		prop.put("jakarta.persistence.schema-generation.create-script-source",
			"META-INF/database/create-db.sql");
		prop.put("jakarta.persistence.schema-generation.drop-script-source",
			"META-INF/database/drop-db.sql");
		
		prop.put("jakarta.persistence.sql-load-script-source",
			"META-INF/database/initial_data.sql");
		
		EntityManagerFactory entityManagerFactory = Persistence
			.createEntityManagerFactory("algaworks-ecommerce", prop);
		
		entityManagerFactory.close();
	}
	
}
