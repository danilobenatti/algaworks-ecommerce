package com.algaworks.ecommerce.listener;

import java.util.logging.Level;
import java.util.logging.Logger;

import jakarta.persistence.PostLoad;

public class GenericListener {
	
	static Logger logger = Logger.getLogger(GenericListener.class.getName());
	
	@PostLoad
	public void entityLoading(Object object) {
		logger.log(Level.INFO, "Entity {0} was loaded!",
				object.getClass().getSimpleName());
	}
	
}
