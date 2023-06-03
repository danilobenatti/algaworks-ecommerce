package com.algaworks.ecommerce.criteria;

import static com.algaworks.ecommerce.util.DatesFunctions.getAge;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.algaworks.ecommerce.EntityManagerTest;
import com.algaworks.ecommerce.model.Category;
import com.algaworks.ecommerce.model.Category_;
import com.algaworks.ecommerce.model.Order;
import com.algaworks.ecommerce.model.OrderItem;
import com.algaworks.ecommerce.model.OrderItemPk_;
import com.algaworks.ecommerce.model.OrderItem_;
import com.algaworks.ecommerce.model.Order_;
import com.algaworks.ecommerce.model.Person;
import com.algaworks.ecommerce.model.Person_;
import com.algaworks.ecommerce.model.Product;
import com.algaworks.ecommerce.model.Product_;

import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.criteria.SetJoin;
import jakarta.persistence.criteria.Subquery;

class SubQueriesCriteriaTest extends EntityManagerTest {
	
	NumberFormat currency = NumberFormat.getCurrencyInstance();
	
	@Test
	void searchSubQueries() {
		// Most expensive products
		/**
		 * JPQL = select p from Product p where p.unitPrice = (select
		 * max(unitPrice) from Product)
		 */
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Product> criteriaQuery = criteriaBuilder
			.createQuery(Product.class);
		Root<Product> root = criteriaQuery.from(Product.class);
		
		criteriaQuery.select(root);
		
		Subquery<BigDecimal> subquery = criteriaQuery
			.subquery(BigDecimal.class);
		Root<Product> rootSubquery = subquery.from(Product.class);
		subquery
			.select(criteriaBuilder.max(rootSubquery.get(Product_.unitPrice)));
		
		criteriaQuery.where(
			criteriaBuilder.equal(root.get(Product_.unitPrice), subquery));
		
		TypedQuery<Product> typedQuery = entityManager
			.createQuery(criteriaQuery);
		List<Product> list = typedQuery.getResultList();
		
		list.forEach(p -> logger.info(String.format("Product Id: %d; price: %s",
			p.getId(), currency.format(p.getUnitPrice()))));
		
		assertFalse(list.isEmpty());
	}
	
	@Test
	void searchSubQueries2() {
		// All orders above sales average
		/*
		 * JPQL = select o from Order o where o.total > (select avg(total) from
		 * Order)
		 */
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Order> criteriaQuery = criteriaBuilder
			.createQuery(Order.class);
		Root<Order> root = criteriaQuery.from(Order.class);
		
		criteriaQuery.select(root);
		
		Subquery<BigDecimal> subquery = criteriaQuery
			.subquery(BigDecimal.class);
		Root<Order> rootSubquery = subquery.from(Order.class);
		subquery.select(criteriaBuilder.avg(rootSubquery.get(Order_.total))
			.as(BigDecimal.class));
		
		criteriaQuery.where(
			criteriaBuilder.greaterThan(root.get(Order_.total), subquery));
		
		TypedQuery<Order> typedQuery = entityManager.createQuery(criteriaQuery);
		List<Order> list = typedQuery.getResultList();
		
		list.forEach(o -> logger.info(String.format("Order Id: %d; total: %s",
			o.getId(), currency.format(o.getTotal()))));
		
		assertFalse(list.isEmpty());
	}
	
	@Test
	void searchSubQueries3() {
		// Good customers(person)
		/*
		 * JPQL = select p from Person p where 600 < (select sum(o.total) from
		 * Order o where o.person = p)
		 */
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Object[]> criteriaQuery = criteriaBuilder
			.createQuery(Object[].class);
		Root<Person> root = criteriaQuery.from(Person.class);
		
		criteriaQuery.multiselect(root, criteriaBuilder.function(
			"calc_total_by_person", Double.class, root.get(Person_.id)));
		
		Subquery<BigDecimal> subquery = criteriaQuery
			.subquery(BigDecimal.class);
		Root<Order> subqueryRoot = subquery.from(Order.class);
		subquery.select(criteriaBuilder.sum(subqueryRoot.get(Order_.total)));
//		subquery.where(criteriaBuilder.equal(root.get(Person_.id),
//			subqueryRoot.get(Order_.person).get(Person_.id)));
		subquery.where(
			criteriaBuilder.equal(root, subqueryRoot.get(Order_.person)));
		
		criteriaQuery.where(
			criteriaBuilder.greaterThan(subquery, BigDecimal.valueOf(600)));
		
		TypedQuery<Object[]> typedQuery = entityManager
			.createQuery(criteriaQuery);
		List<Object[]> list = typedQuery.getResultList();
		
		list.forEach(p -> logger
			.info(String.format("Person Id: %d; Name: %s; Age: %d; Total: %s",
				((Person) p[0]).getId(), ((Person) p[0]).getFirstname(),
				getAge(((Person) p[0]).getBirthday()), currency.format(p[1]))));
		
		assertFalse(list.isEmpty());
	}
	
	@Test
	void searchWithIn() {
		/*
		 * JPQL = select o from Order o where o.id in (select o2.id from
		 * OrderItem i2 join i2.order o2 join i2.product p2 where p2.unitPrice >
		 * 1000.0)
		 */
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Order> criteriaQuery = criteriaBuilder
			.createQuery(Order.class);
		Root<Order> root = criteriaQuery.from(Order.class);
		
		criteriaQuery.select(root);
		
		Subquery<Long> subquery = criteriaQuery.subquery(long.class);
		Root<OrderItem> subqueryRoot = subquery.from(OrderItem.class);
		Join<OrderItem, Order> subqueryJoinOrder = subqueryRoot
			.join(OrderItem_.order);
		Join<OrderItem, Product> subqueryJoinProduct = subqueryRoot
			.join(OrderItem_.product);
		subquery.select(subqueryJoinOrder.get(Order_.id));
		subquery.where(criteriaBuilder.greaterThan(
			subqueryJoinProduct.get(Product_.unitPrice),
			BigDecimal.valueOf(1000.0)));
		
		criteriaQuery.where(root.get(Order_.id).in(subquery));
		
		TypedQuery<Order> typedQuery = entityManager.createQuery(criteriaQuery);
		List<Order> list = typedQuery.getResultList();
		
		list.forEach(o -> logger
			.info(new StringBuilder().append("Order ID: ").append(o.getId())
				.append("; Total: ").append(currency.format(o.getTotal()))));
		
		assertFalse(list.isEmpty());
	}
	
	@Test
	void searchExists() {
		/*
		 * JPQL = select p1 from Product p1 where exists (select 1 from
		 * OrderItem i2 join i2.product p2 where p2 = p1)
		 */
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Product> criteriaQuery = criteriaBuilder
			.createQuery(Product.class);
		Root<Product> root = criteriaQuery.from(Product.class);
		
		criteriaQuery.select(root);
		
		Subquery<Integer> subquery = criteriaQuery.subquery(Integer.class);
		Root<OrderItem> subqueryRoot = subquery.from(OrderItem.class);
		subquery.select(criteriaBuilder.literal(1));
		subquery.where(
			criteriaBuilder.equal(subqueryRoot.get(OrderItem_.product), root));
		
		criteriaQuery.where(criteriaBuilder.exists(subquery));
		
		TypedQuery<Product> typedQuery = entityManager
			.createQuery(criteriaQuery);
		List<Product> list = typedQuery.getResultList();
		
		list.forEach(p -> logger.info(new StringBuilder().append("Product Id: ")
			.append(p.getId()).append("; Name: ").append(p.getName())));
		
		assertFalse(list.isEmpty());
	}
	
	@Test
	void searchPersonWithOrdersExercise() {
		/*
		 * JPQL = select p from Person p where (select count(o) from Order o
		 * where person = p) >= 2
		 */
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Person> criteriaQuery = criteriaBuilder
			.createQuery(Person.class);
		Root<Person> root = criteriaQuery.from(Person.class);
		
		criteriaQuery.select(root);
		
		Subquery<Long> subquery = criteriaQuery.subquery(Long.class);
		Root<Order> subqueryRoot = subquery.from(Order.class);
		subquery.select(criteriaBuilder.count(subqueryRoot.get(Order_.id)));
		subquery.where(
			criteriaBuilder.equal(subqueryRoot.get(Order_.person), root));
		
		criteriaQuery.where(criteriaBuilder.greaterThanOrEqualTo(subquery, 2L));
		
		TypedQuery<Person> typedQuery = entityManager
			.createQuery(criteriaQuery);
		List<Person> list = typedQuery.getResultList();
		
		list.forEach(p -> logger.info(new StringBuilder().append("Person Id: ")
			.append(p.getId()).append("; Name: ").append(p.getFirstname())));
		
		assertFalse(list.isEmpty());
	}
	
	@Test
	void searchOrderWithCategoryInExercise() {
		/*
		 * JPQL = select o1 from Order o1 where o1.id in (select o2.id from
		 * OrderItem i2 join i2.order o2 join i2.product p2 join p2.categories
		 * c2 where c2.id = 2)
		 */
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Order> criteriaQuery = criteriaBuilder
			.createQuery(Order.class);
		Root<Order> root = criteriaQuery.from(Order.class);
		
		criteriaQuery.select(root);
		
		Subquery<Long> subquery = criteriaQuery.subquery(Long.class);
		Root<OrderItem> subqueryRoot = subquery.from(OrderItem.class);
		Join<OrderItem, Product> subqueryJoinProduct = subqueryRoot
			.join(OrderItem_.product);
		SetJoin<Product, Category> subqueryJoinCategory = subqueryJoinProduct
			.join(Product_.categories);
		subquery
			.select(subqueryRoot.get(OrderItem_.id).get(OrderItemPk_.orderId));
		subquery.where(
			criteriaBuilder.equal(subqueryJoinCategory.get(Category_.id), 2L));
		
		criteriaQuery.where(root.get(Order_.id).in(subquery));
		
		TypedQuery<Order> typedQuery = entityManager.createQuery(criteriaQuery);
		
		List<Order> list = typedQuery.getResultList();
		assertFalse(list.isEmpty());
		
		list.forEach(o -> logger
			.info(new StringBuilder().append("Order Id: ").append(o.getId())
				.append("; Total: ").append(currency.format(o.getTotal()))));
		
		assertFalse(list.isEmpty());
	}
	
	@Test
	void searchOrderWithExistsExercise() {
		/*
		 * JPQL = select p from Product p where p.unitPrice <> any (select
		 * i1.subtotal/i1.quantity from OrderItem i1 where i1.product = p)
		 */
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Product> criteriaQuery = criteriaBuilder
			.createQuery(Product.class);
		Root<Product> root = criteriaQuery.from(Product.class);
		
		criteriaQuery.select(root);
		
		Subquery<Integer> subquery = criteriaQuery.subquery(Integer.class);
		Root<OrderItem> subqueryRoot = subquery.from(OrderItem.class);
		subquery.select(criteriaBuilder.literal(1));
		subquery.where(
			criteriaBuilder.equal(subqueryRoot.get(OrderItem_.product), root),
			criteriaBuilder.notEqual(
				criteriaBuilder.quot(subqueryRoot.get(OrderItem_.subtotal),
					subqueryRoot.get(OrderItem_.quantity)),
				root.get(Product_.unitPrice)));
		
		criteriaQuery.where(criteriaBuilder.exists(subquery));
		
		TypedQuery<Product> typedQuery = entityManager
			.createQuery(criteriaQuery);
		
		List<Product> list = typedQuery.getResultList();
		
		list.forEach(
			p -> logger.info(String.format("%d - %s", p.getId(), p.getName())));
		
		assertFalse(list.isEmpty());
	}
	
	@Test
	void searchAll1() {
		// Find products that have always sold at the current price
		/*
		 * JPQL = select p1 from Product p1 where p1.unitPrice = all (select
		 * i2.subtotal/i2.quantity from OrderItem i2 where i2.product = p1)
		 */
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Product> criteriaQuery = criteriaBuilder
			.createQuery(Product.class);
		Root<Product> root = criteriaQuery.from(Product.class);
		
		criteriaQuery.select(root);
		
		Subquery<BigDecimal> subquery = criteriaQuery
			.subquery(BigDecimal.class);
		Root<OrderItem> subqueryRoot = subquery.from(OrderItem.class);
		subquery
			.select(criteriaBuilder
				.quot(subqueryRoot.get(OrderItem_.subtotal),
					subqueryRoot.get(OrderItem_.quantity))
				.as(BigDecimal.class));
		subquery.from(OrderItem.class);
		subquery.where(
			criteriaBuilder.equal(subqueryRoot.get(OrderItem_.product), root));
		
		criteriaQuery.where(criteriaBuilder.equal(root.get(Product_.unitPrice),
			criteriaBuilder.all(subquery)));
		
		TypedQuery<Product> typedQuery = entityManager
			.createQuery(criteriaQuery);
		List<Product> list = typedQuery.getResultList();
		
		list.forEach(p -> logger.info(
			new StringBuilder().append("Product ID: ").append(p.getId())));
		
		assertFalse(list.isEmpty());
	}
	
	@Test
	void searchAll2() {
		// Find products that have not sold after the price increased
		/*
		 * JPQL = select p1 from Product p1 where p1.unitPrice > all (select
		 * i2.subtotal/i2.quantity from OrderItem i2 where i2.product = p1) and
		 * exists (select 1 from OrderItem i2 where i2.product = p1)
		 */
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Product> criteriaQuery = criteriaBuilder
			.createQuery(Product.class);
		Root<Product> root = criteriaQuery.from(Product.class);
		
		criteriaQuery.select(root);
		
		Subquery<BigDecimal> subquery = criteriaQuery
			.subquery(BigDecimal.class);
		Root<OrderItem> subqueryRoot = subquery.from(OrderItem.class);
		subquery
			.select(criteriaBuilder
				.quot(subqueryRoot.get(OrderItem_.subtotal),
					subqueryRoot.get(OrderItem_.quantity))
				.as(BigDecimal.class));
		subquery.from(OrderItem.class);
		subquery.where(
			criteriaBuilder.equal(subqueryRoot.get(OrderItem_.product), root));
		
		criteriaQuery.where(
			criteriaBuilder.greaterThan(root.get(Product_.unitPrice),
				criteriaBuilder.all(subquery)),
			criteriaBuilder.exists(subquery));
		
		TypedQuery<Product> typedQuery = entityManager
			.createQuery(criteriaQuery);
		List<Product> list = typedQuery.getResultList();
		
		list.forEach(p -> logger.info(new StringBuilder().append("Product ID: ")
			.append(p.getId()).append("; Name: ").append(p.getName())));
		
		assertFalse(list.isEmpty());
	}
	
	@Test
	void searchWithAny1() {
		// All products sold at least once at current price.
		/*
		 * JPQL = select p from Product p where p.unitPrice = any (select
		 * i1.subtotal/i1.quantity from OrderItem i1 where i1.product = p)
		 */
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Product> criteriaQuery = criteriaBuilder
			.createQuery(Product.class);
		Root<Product> root = criteriaQuery.from(Product.class);
		
		criteriaQuery.select(root);
		
		Subquery<BigDecimal> subquery = criteriaQuery
			.subquery(BigDecimal.class);
		Root<OrderItem> subqueryRoot = subquery.from(OrderItem.class);
		subquery
			.select(criteriaBuilder
				.quot(subqueryRoot.get(OrderItem_.subtotal),
					subqueryRoot.get(OrderItem_.quantity))
				.as(BigDecimal.class));
		subquery.from(OrderItem.class);
		subquery.where(
			criteriaBuilder.equal(subqueryRoot.get(OrderItem_.product), root));
		
		criteriaQuery.where(criteriaBuilder.equal(root.get(Product_.unitPrice),
			criteriaBuilder.any(subquery)));
		
		TypedQuery<Product> typedQuery = entityManager
			.createQuery(criteriaQuery);
		List<Product> list = typedQuery.getResultList();
		
		list.forEach(p -> logger
			.info(new StringBuilder().append("Product ID: ").append(p.getId())
				.append("; Name: ").append(p.getName()).append("; Unit Price: ")
				.append(currency.format(p.getUnitPrice()))));
		
		assertFalse(list.isEmpty());
	}
	
	@Test
	void searchWithAny2() {
		// All products sold at a different price than the current one.
		/*
		 * JPQL = select p from Product p where p.unitPrice <> any (select
		 * i1.subtotal/i1.quantity from OrderItem i1 where i1.product = p)
		 */
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Product> criteriaQuery = criteriaBuilder
			.createQuery(Product.class);
		Root<Product> root = criteriaQuery.from(Product.class);
		
		criteriaQuery.select(root);
		
		Subquery<BigDecimal> subquery = criteriaQuery
			.subquery(BigDecimal.class);
		Root<OrderItem> subqueryRoot = subquery.from(OrderItem.class);
		subquery
			.select(criteriaBuilder
				.quot(subqueryRoot.get(OrderItem_.subtotal),
					subqueryRoot.get(OrderItem_.quantity))
				.as(BigDecimal.class));
		subquery.from(OrderItem.class);
		subquery.where(
			criteriaBuilder.equal(subqueryRoot.get(OrderItem_.product), root));
		
		criteriaQuery.where(criteriaBuilder.notEqual(
			root.get(Product_.unitPrice), criteriaBuilder.any(subquery)));
		
		TypedQuery<Product> typedQuery = entityManager
			.createQuery(criteriaQuery);
		List<Product> list = typedQuery.getResultList();
		
		list.forEach(p -> logger
			.info(new StringBuilder().append("Product ID: ").append(p.getId())
				.append("; Name: ").append(p.getName()).append("; Unit Price: ")
				.append(currency.format(p.getUnitPrice()))));
		
		assertFalse(list.isEmpty());
	}
	
}
