package com.algaworks.ecommerce.service;

import java.util.logging.Logger;

import com.algaworks.ecommerce.model.Order;

public class InvoiceService {
	
	static Logger logger = Logger.getLogger(InvoiceService.class.getName());
	
	public void generate(Order order) {
		logger.info(() -> String.format("Generated invoice for order: %s.",
				order.getId()));
	}
	
}
