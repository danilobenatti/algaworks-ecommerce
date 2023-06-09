package com.algaworks.ecommerce.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.config.Configurator;

import com.algaworks.ecommerce.model.Order;

public class InvoiceService {
	
	protected static Logger logger = LogManager.getLogger();
	
	public void generate(Order order) {
		
		Configurator.initialize(InvoiceService.class.getName(),
			"./src/main/resources/log4j2.properties");
		
		logger.info(() -> String.format("Generated invoice for order: %s.",
				order.getId()));
	}
	
}
