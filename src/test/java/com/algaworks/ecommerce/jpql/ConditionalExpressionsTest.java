package com.algaworks.ecommerce.jpql;

import static org.junit.jupiter.api.Assertions.assertFalse;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import com.algaworks.ecommerce.EntityManagerTest;
import com.algaworks.ecommerce.model.Order;
import com.algaworks.ecommerce.model.Person;
import com.algaworks.ecommerce.model.Product;

import jakarta.persistence.TypedQuery;

class ConditionalExpressionsTest extends EntityManagerTest {
	
	@Test
	void usingCondicionalExpressionLike() {
		String jpql = "select p from Person p where p.firstname like concat('%', :name, '%')";
		
		TypedQuery<Object[]> typedQuery = entityManager.createQuery(jpql,
			Object[].class);
		typedQuery.setParameter("name", "m");
		
		List<Object[]> resultList = typedQuery.getResultList();
		assertFalse(resultList.isEmpty());
		
	}
	
	void test(String jpql) {
		TypedQuery<Object[]> typedQuery = entityManager.createQuery(jpql,
			Object[].class);
		
		List<Object[]> resultList = typedQuery.getResultList();
		assertFalse(resultList.isEmpty());
	}
	
	@ParameterizedTest
	@ValueSource(strings = {
		"select p from Product p where p.image is not null",
		"select p from Product p where p.image is null",
		"select p from Product p where p.categories is empty",
		"select p from Product p where p.categories is not empty order by p.id" })
	void using(String jpql) {
		// using IsNotNull
		// using IsNull
		// using IsEmpty
		// using IsNotEmpty
		test(jpql);
	}
	
	@Test
	void usingGreaterMinor() {
		String jpql = "select p from Product p where p.unitPrice >= :start and p.unitPrice <= :end";
		
		TypedQuery<Product> typedQuery = entityManager.createQuery(jpql,
			Product.class);
		typedQuery.setParameter("start", BigDecimal.valueOf(499.50));
		typedQuery.setParameter("end", BigDecimal.valueOf(1600.00));
		
		List<Product> resultList = typedQuery.getResultList();
		assertFalse(resultList.isEmpty());
		
	}
	
	@Test
	void usingGreaterMinorWithDate() {
		String jpql = "select o from Order o where o.dateCreate >= :date";
		
		TypedQuery<Object[]> typedQuery = entityManager.createQuery(jpql,
			Object[].class);
		typedQuery.setParameter("date", LocalDateTime.now().minusYears(1));
		
		List<Object[]> resultList = typedQuery.getResultList();
		assertFalse(resultList.isEmpty());
		
	}
	
	@Test
	void usingBetweenProduct() {
		String jpql = "select p from Product p where p.unitPrice between :start and :end";
		
		TypedQuery<Product> typedQuery = entityManager.createQuery(jpql,
			Product.class);
		typedQuery.setParameter("start", BigDecimal.valueOf(499.50));
		typedQuery.setParameter("end", BigDecimal.valueOf(1506.72));
		
		List<Product> resultList = typedQuery.getResultList();
		assertFalse(resultList.isEmpty());
		
	}
	
	@Test
	void usingBetweenOrders() {
		String jpql = "select o from Order o where o.dateCreate between :start and :end";
		
		TypedQuery<Object[]> typedQuery = entityManager.createQuery(jpql,
			Object[].class);
		typedQuery.setParameter("start", LocalDateTime.now().minusYears(1));
		typedQuery.setParameter("end", LocalDateTime.now());
		
		List<Object[]> resultList = typedQuery.getResultList();
		assertFalse(resultList.isEmpty());
		
	}
	
	@Test
	void usingDifferentExpression() {
		String jpql = "select p from Product p where p.unit <> 1";
		
		TypedQuery<Product> typedQuery = entityManager.createQuery(jpql,
			Product.class);
		List<Product> list = typedQuery.getResultList();
		
		assertFalse(list.isEmpty());
		
	}
	
	@ParameterizedTest
	@ValueSource(strings = {
		"select o.id, case o.status when 1 then 'Waiting' when 2 then 'Canceled' when 3 then 'Paid' "
			+ "else 'Status undefined' end from Order o",
		"select p.order.id, case p.status when 1 then 'Processing' when 2 then 'Canceled' when 3 then 'Received' "
			+ "else 'Status undefined' end from Payment p",
		"select o.id, case type(o.payment) when PaymentCreditCard then 'Credit Card payment' "
			+ "when PaymentBankSlip then 'Bank Slip payment' "
			+ "else 'Payment undefined' end from Order o" })
	void usingCaseExpression(String jpql) {
		/*
		 * first case: select o.id, case o.col_status when 1 then 'Processing'
		 * when 2 then 'Canceled' when 3 then 'Received' else 'Order Status
		 * undefined' end as 'status' from tbl_orders o;
		 * 
		 * select o.id as order_id, case o.id when pb.order_id then 'Bank Slip
		 * payment' when pc.order_id then 'Credit Card payment' else 'Payment
		 * undefined' end as 'payment' from tbl_orders o left join tbl_payments
		 * p on p.order_id = o.id left join tbl_payments_bankslip pb on
		 * pb.order_id = p.order_id left join tbl_payments_creditcard pc on
		 * pc.order_id = p.order_id;
		 * 
		 */
		
		TypedQuery<Object[]> typedQuery = entityManager.createQuery(jpql,
			Object[].class);
		
		List<Object[]> resultList = typedQuery.getResultList();
		assertFalse(resultList.isEmpty());
		
		resultList
			.forEach(i -> logger.info(String.format("%s, %s", i[0], i[1])));
	}
	
	@ParameterizedTest
	@ValueSource(strings = {
		"select o.id, o.status from Order o where o.id in (:list)" })
	void usingInExpression(String jpql) {
		TypedQuery<Object[]> typedQuery = entityManager.createQuery(jpql,
			Object[].class);
//		List<Long> list = Arrays.asList(1L, 2L, 3L, 4L);
		Set<Long> list = new HashSet<>();
		Collections.addAll(list, 1L, 2L, 3L, 4L);
		typedQuery.setParameter("list", list);
		
		List<Object[]> resultList = typedQuery.getResultList();
		assertFalse(resultList.isEmpty());
		
		resultList
			.forEach(i -> logger.info(String.format("%s, %s", i[0], i[1])));
	}
	
	@ParameterizedTest
	@ValueSource(strings = {
		"select o from Order o where o.person in (:list) order by o.person.id" })
	void usingInExpression2(String jpql) {
		Person p1 = entityManager.find(Person.class, 1L);
		Person p2 = entityManager.find(Person.class, 2L);
		Person p3 = entityManager.find(Person.class, 3L);
		List<Person> persons = Arrays.asList(p1, p2, p3);
//		Set<Person> persons = new HashSet<>();
//		Collections.addAll(persons, p1, p2, p3);
		
		TypedQuery<Order> typedQuery = entityManager.createQuery(jpql,
			Order.class);
		typedQuery.setParameter("list", persons);
		
		List<Order> resultList = typedQuery.getResultList();
		assertFalse(resultList.isEmpty());
		
		resultList.forEach(o -> logger.info(
			String.format("%s, %s", o.getId(), o.getPerson().getFirstname())));
	}
	
}
