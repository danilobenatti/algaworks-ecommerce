package com.algaworks.ecommerce.importantdetails;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.algaworks.ecommerce.EntityManagerTest;
import com.algaworks.ecommerce.model.Product;
import com.algaworks.ecommerce.model.enums.ProductUnit;

class ConverterTest extends EntityManagerTest {
	
	@Test
	void converter() {
		Product product = new Product();
		product.setName("Produto teste converter");
		product.setDescription("Descrição do produto teste");
		product.setUnit(ProductUnit.KILOGRAM);
		product.setActive(true);
		
		entityManager.getTransaction().begin();
		entityManager.persist(product);
		entityManager.getTransaction().commit();
		entityManager.clear();
		
		Product findProduct = entityManager.find(Product.class,
			product.getId());
		Assertions.assertTrue(findProduct.getActive());
	}
}
