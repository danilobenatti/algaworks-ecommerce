package com.algaworks.ecommerce.relationships;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import com.algaworks.ecommerce.EntityManagerTest;
import com.algaworks.ecommerce.model.Category;

class AutoRelationshipsTest extends EntityManagerTest {
	
	@Test
	void checkRelationshipsTest() {
		Category parentCategory = new Category();
		parentCategory.setName("Electronics");
		
		Category childCategory = new Category();
		childCategory.setName("Computers");
		childCategory.setParentCategory(parentCategory);
		
		entityManager.getTransaction().begin();
		entityManager.persist(parentCategory);
		entityManager.persist(childCategory);
		entityManager.getTransaction().commit();
		
		entityManager.clear();
		
		Category c1 = entityManager.find(Category.class,
			parentCategory.getId());
		Category c2 = entityManager.find(Category.class, childCategory.getId());
		
		assertEquals(childCategory.getParentCategory().getId(), c1.getId());
		assertEquals(childCategory.getId(), c2.getId());
		
	}
	
}
