package com.algaworks.ecommerce.criteria;

import static com.algaworks.ecommerce.util.DatesFunctions.getAge;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.math.BigDecimal;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.algaworks.ecommerce.EntityManagerTest;
import com.algaworks.ecommerce.dto.ProductDTO;
import com.algaworks.ecommerce.model.Order;
import com.algaworks.ecommerce.model.Person;
import com.algaworks.ecommerce.model.Person_;
import com.algaworks.ecommerce.model.Product;

import jakarta.persistence.Tuple;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

class BasicCriteriaTest extends EntityManagerTest {
	
	@Test
	void searchById() {
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Order> query = builder.createQuery(Order.class);
		Root<Order> root = query.from(Order.class);
		
		query.select(root); // it is implied
		query.where(builder.equal(root.get("id"), 1L));
		/*
		 * String jpql = "select o from Order o where o.id = 1";
		 */
		TypedQuery<Order> typedQuery = entityManager.createQuery(query);
		Order order = typedQuery.getSingleResult();
		assertNotNull(order);
	}
	
	@Test
	void selectPersonFromOrder() {
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Person> query = builder.createQuery(Person.class);
		Root<Order> root = query.from(Order.class);
		
		query.select(root.get("person"));
		query.where(builder.equal(root.get("id"), 1L));
		/*
		 * String jpql = "select o.person from Order o where o.id = 1";
		 */
		TypedQuery<Person> typedQuery = entityManager.createQuery(query);
		Person person = typedQuery.getSingleResult();
		assertEquals("Luiz Fernando", person.getFirstname());
	}
	
	@Test
	void selectTotalFromOrder() {
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<BigDecimal> query = builder.createQuery(BigDecimal.class);
		Root<Order> root = query.from(Order.class);
		
		query.select(root.get("total"));
		query.where(builder.equal(root.get("id"), 1L));
		/*
		 * String jpql = "select o.total from Order o where o.id = 1"
		 */
		TypedQuery<BigDecimal> typedQuery = entityManager.createQuery(query);
		BigDecimal total = typedQuery.getSingleResult();
		assertEquals(new BigDecimal("505.00"), total);
	}
	
	@Test
	void selectAllProducts() {
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Product> query = builder.createQuery(Product.class);
		Root<Product> root = query.from(Product.class);
		
		query.select(root);
		/*
		 * String jpql = "select p from Product p"
		 */
		TypedQuery<Product> typedQuery = entityManager.createQuery(query);
		List<Product> resultList = typedQuery.getResultList();
		assertFalse(resultList.isEmpty());
	}
	
	@Test
	void resultProjection() {
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Object[]> query = builder.createQuery(Object[].class);
		Root<Product> root = query.from(Product.class);
		
		query.multiselect(root.get("id"), root.get("name"));
		/*
		 * String jpql = "select p.id, p.name from Product p"
		 */
		TypedQuery<Object[]> typedQuery = entityManager.createQuery(query);
		List<Object[]> resultList = typedQuery.getResultList();
		
		resultList
			.forEach(o -> logger.info(String.format("%s - %s", o[0], o[1])));
		assertFalse(resultList.isEmpty());
	}
	
	@Test
	void resultProjectionTuple() {
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Tuple> query = builder.createTupleQuery();
		Root<Product> root = query.from(Product.class);
		
		query.select(builder.tuple(root.get("id").alias("id"),
			root.get("name").alias("name")));
		/*
		 * String jpql = "select p.id, p.name from Product p"
		 */
		TypedQuery<Tuple> typedQuery = entityManager.createQuery(query);
		List<Tuple> resultList = typedQuery.getResultList();
		
		resultList.forEach(t -> logger
			.info(String.format("%s - %s", t.get("id"), t.get("name"))));
		assertFalse(resultList.isEmpty());
	}
	
	@Test
	void resultProjectionFromDTO() {
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<ProductDTO> query = builder.createQuery(ProductDTO.class);
		Root<Product> root = query.from(Product.class);
		
		query.select(builder.construct(ProductDTO.class, root.get("id"),
			root.get("name")));
		/*
		 * String jpql = "select new com.algaworks.ecommerce.dto.ProductDTO(id,
		 * name) from Product"
		 */
		TypedQuery<ProductDTO> typedQuery = entityManager.createQuery(query);
		List<ProductDTO> resultList = typedQuery.getResultList();
		
		resultList.forEach(dto -> logger
			.info(String.format("%s - %s", dto.getId(), dto.getName())));
		assertFalse(resultList.isEmpty());
	}
	
	@Test
	void sortResults() {
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Person> query = builder.createQuery(Person.class);
		Root<Person> root = query.from(Person.class);
		
		query.select(root);
		query.orderBy(builder.asc(root.get(Person_.firstname)),
			builder.desc(root.get(Person_.birthday)));
		
		TypedQuery<Person> typedQuery = entityManager.createQuery(query);
		List<Person> resultList = typedQuery.getResultList();
		
		resultList.forEach(p -> logger.info(String.format("%d - %s - %d",
			p.getId(), p.getFirstname(), getAge(p.getBirthday()))));
		assertFalse(resultList.isEmpty());
	}
	
}