package com.algaworks.ecommerce.jpql;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.algaworks.ecommerce.EntityManagerTest;
import com.algaworks.ecommerce.model.Product;

import jakarta.persistence.TypedQuery;

class DynamicQueryTest extends EntityManagerTest {
	
	@Test
	void executeDynamicQuery() {
		Product product = new Product();
		product.setName("kin");
		
		List<Product> list = getProductList(product);
		
		Assertions.assertFalse(list.isEmpty());
		Assertions.assertEquals("Kindle", list.get(0).getName());
	}
	
	private List<Product> getProductList(Product product) {
		StringBuilder jpql = new StringBuilder();
		jpql.append("select p from Product p where 1 = 1 ");
		
		if (product.getName() != null && !product.getName().isEmpty()) {
			jpql.append("and p.name like concat('%', :name, '%')");
		}
		if (product.getDescription() != null
			&& !product.getDescription().isEmpty()) {
			jpql.append("and p.description like concat('%', :descript, '%')");
		}
		
		TypedQuery<Product> typedQuery = entityManager
			.createQuery(jpql.toString(), Product.class);
		
		if (product.getName() != null && !product.getName().isEmpty()) {
			typedQuery.setParameter("name", product.getName());
		}
		if (product.getDescription() != null
			&& !product.getDescription().isEmpty()) {
			typedQuery.setParameter("descript", product.getDescription());
		}
		return typedQuery.getResultList();
	}
	
}
