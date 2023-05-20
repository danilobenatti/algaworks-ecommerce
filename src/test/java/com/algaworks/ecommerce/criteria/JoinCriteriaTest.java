package com.algaworks.ecommerce.criteria;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.junit.jupiter.api.Test;

import com.algaworks.ecommerce.EntityManagerTest;
import com.algaworks.ecommerce.model.Order;
import com.algaworks.ecommerce.model.Payment;
import com.algaworks.ecommerce.model.enums.OrderStatus;
import com.algaworks.ecommerce.model.enums.PaymentStatus;

import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Root;

class JoinCriteriaTest extends EntityManagerTest {
	
	static Logger logger = Logger.getLogger(JoinCriteriaTest.class.getName());
	
	@Test
	void makeJoinWithCriteria() {
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Order> query = builder.createQuery(Order.class);
		Root<Order> root = query.from(Order.class);
		Join<Order, Payment> joinPayment = root.join("payment");
//		Join<Order, OrderItem> joinItems = root.join("orderitems");
//		Join<OrderItem, Product> joinProduct = joinItems.join("product");
		
		query.select(root);
		query.where(builder.equal(joinPayment.get("status"), 1));
		/*
		 * String jpql =
		 * "select o from Order o join Payment p join OrderItem i join Product p"
		 */
		TypedQuery<Order> typedQuery = entityManager.createQuery(query);
		List<Order> resultList = typedQuery.getResultList();
		
		resultList.forEach(o -> logger.log(Level.INFO, "{0}",
			String.format("%d - %s", o.getId(), o.getStatus())));
		
		assertFalse(resultList.isEmpty());
	}
	
	@Test
	void makeJoinWithCriteria1() {
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Order> query = builder.createQuery(Order.class);
		Root<Order> root = query.from(Order.class);
		Join<Order, Payment> join = root.join("payment");
		
		query.select(root);
		query.where(builder.equal(join.get("status"),
			PaymentStatus.PROCESSING.getCode()));
		/*
		 * String jpql =
		 * "select o from Order o join Payment p where p.col_status = 1"
		 */
		TypedQuery<Order> typedQuery = entityManager.createQuery(query);
		List<Order> resultList = typedQuery.getResultList();
		
		resultList.forEach(o -> logger.log(Level.INFO, "{0}",
			String.format("%d - %s", o.getId(), o.getStatus())));
		
		assertFalse(resultList.isEmpty());
	}
	
	@Test
	void makeJoinWithCriteria2() {
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Payment> query = builder.createQuery(Payment.class);
		Root<Order> root = query.from(Order.class);
		Join<Order, Payment> join = root.join("payment");
		
		query.select(join);
		query.where(
			builder.equal(root.get("status"), OrderStatus.WAITING.getCode()));
		/*
		 * String jpql =
		 * "select p from Order o join Payment p where o.status = 1"
		 * 
		 */
		TypedQuery<Payment> typedQuery = entityManager.createQuery(query);
		List<Payment> resultList = typedQuery.getResultList();
		
		resultList.forEach(o -> logger.log(Level.INFO, "{0}",
			String.format("%d - %s", o.getId(), o.getStatus())));
		
		assertFalse(resultList.isEmpty());
	}
	
	@Test
	void makeJoinOnWithCriteria() {
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Order> query = builder.createQuery(Order.class);
		Root<Order> root = query.from(Order.class);
		Join<Order, Payment> joinPayment = root.join("payment");
		joinPayment.on(builder.equal(joinPayment.get("status"),
			PaymentStatus.PROCESSING.getCode()));
		
		query.select(root);
		/*
		 * SQL = "select o.id, o.col_status as 'status' from tbl_orders o join
		 * tbl_payments p on p.order_id = o.id and p.col_status = 1"
		 */
		TypedQuery<Order> typedQuery = entityManager.createQuery(query);
		List<Order> resultList = typedQuery.getResultList();
		
		resultList.forEach(o -> logger.log(Level.INFO, "{0}",
			String.format("%d - %s", o.getId(), o.getStatus())));
		
		assertFalse(resultList.isEmpty());
	}
	
	@Test
	void makeLeftOuterJoinWithCriteria() {
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Order> query = builder.createQuery(Order.class);
		Root<Order> root = query.from(Order.class);
//		Join<Order, Payment> join = root.join("payment", JoinType.LEFT);
		root.join("payment", JoinType.LEFT);
		
		query.select(root);
		/*
		 * SQL = "select o.id, o.col_status, p.col_status from tbl_orders o left
		 * outer join tbl_payments p on p.order_id = o.id"
		 */
		TypedQuery<Order> typedQuery = entityManager.createQuery(query);
		List<Order> resultList = typedQuery.getResultList();
		
		resultList.forEach(o -> logger.log(Level.INFO, "{0}",
			String.format("%d - %s - %s", o.getId(), o.getStatus(),
				o.getPayment() != null ? o.getPayment().getStatus() : "N/A")));
		
		assertFalse(resultList.isEmpty());
		
	}
	
	@Test
	void makeJoinFetchWithCriteria() {
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Order> query = builder.createQuery(Order.class);
		Root<Order> root = query.from(Order.class);
		root.fetch("person");
		root.fetch("invoice", JoinType.LEFT);
		root.fetch("payment", JoinType.LEFT);
		
		query.select(root);
		query.where(builder.equal(root.get("id"), 1));
		
		TypedQuery<Order> typedQuery = entityManager.createQuery(query);
		Order order = typedQuery.getSingleResult();
		
		assertNotNull(order);
	}
	
}
