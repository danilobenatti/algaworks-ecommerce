package com.algaworks.ecommerce.criteria;

import static org.junit.jupiter.api.Assertions.assertFalse;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.junit.jupiter.api.Test;

import com.algaworks.ecommerce.EntityManagerTest;
import com.algaworks.ecommerce.model.Order;
import com.algaworks.ecommerce.model.OrderItem;
import com.algaworks.ecommerce.model.OrderItem_;
import com.algaworks.ecommerce.model.Order_;
import com.algaworks.ecommerce.model.Product;
import com.algaworks.ecommerce.model.Product_;

import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Root;

class MetaModelTest extends EntityManagerTest {
	
	static Logger logger = Logger.getLogger(MetaModelTest.class.getName());
	
	@Test
	void usingMetaModel() {
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Product> query = builder.createQuery(Product.class);
		Root<Product> root = query.from(Product.class);
		
		query.select(root);
		
		query.where(builder.or(builder.like(root.get(Product_.name), "%C%"),
			builder.like(root.get(Product_.description), "%C%")));
		
		TypedQuery<Product> typedQuery = entityManager.createQuery(query);
		List<Product> resultList = typedQuery.getResultList();
		
		assertFalse(resultList.isEmpty());
	}
	
	@Test
	void searchOrdersWithProductUsingMetaModel() {
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Order> query = builder.createQuery(Order.class);
		Root<Order> root = query.from(Order.class);
		Join<OrderItem, Product> join = root.join(Order_.orderitems)
			.join(OrderItem_.product);
		
		query.select(root);
		query.where(builder.equal(join.get("id"), 1L));
		
		TypedQuery<Order> typedQuery = entityManager.createQuery(query);
		List<Order> resultList = typedQuery.getResultList();
		
		assertFalse(resultList.isEmpty());
	}
	
	@Test
	void makeLeftOuterJoinWithCriteriaUsingMetaModel() {
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Order> query = builder.createQuery(Order.class);
		Root<Order> root = query.from(Order.class);
		root.join(Order_.payment, JoinType.LEFT);
		
		query.select(root);
		
		TypedQuery<Order> typedQuery = entityManager.createQuery(query);
		List<Order> resultList = typedQuery.getResultList();
		
		resultList.forEach(o -> logger.log(Level.INFO, "{0}",
			String.format("%d - %s - %s", o.getId(), o.getStatus(),
				o.getPayment() != null ? o.getPayment().getStatus() : "N/A")));
		
		assertFalse(resultList.isEmpty());
		
	}
	
}
