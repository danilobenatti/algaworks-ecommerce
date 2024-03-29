package com.algaworks.ecommerce.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.algaworks.ecommerce.listener.GenerateInvoiceListener;
import com.algaworks.ecommerce.listener.GenericListener;
import com.algaworks.ecommerce.model.enums.OrderStatus;

import jakarta.persistence.Cacheable;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedAttributeNode;
import jakarta.persistence.NamedEntityGraph;
import jakarta.persistence.NamedEntityGraphs;
import jakarta.persistence.NamedSubgraph;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Cacheable(value = true)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@NamedEntityGraphs(value = { @NamedEntityGraph(name = "Order.essentialData",
	attributeNodes = { @NamedAttributeNode(value = "executionDate"),
		@NamedAttributeNode(value = "status"),
		@NamedAttributeNode(value = "total"),
		@NamedAttributeNode(value = "person", subgraph = "p") },
	subgraphs = { @NamedSubgraph(name = "p",
		attributeNodes = { @NamedAttributeNode(value = "firstname"),
			@NamedAttributeNode(value = "taxIdNumber") }) }) })
@EntityListeners({ GenerateInvoiceListener.class, GenericListener.class })
@Entity
@Table(name = "tbl_orders")
public class Order extends BaseEntityLong implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Column(name = "col_execution_date", columnDefinition = "timestamp")
	private LocalDateTime executionDate;
	
	@OneToOne(mappedBy = "order", fetch = FetchType.LAZY,
		cascade = CascadeType.REMOVE)
	private Invoice invoice;
	
	@PositiveOrZero(message = "Total to be greater than or equal to zero[0]")
	@NotNull(message = "Total it not be null")
	@Column(name = "col_total", nullable = false)
	private BigDecimal total = BigDecimal.ZERO;
	
	@NotNull(message = "Field [status] is required")
	@Column(name = "col_status", nullable = false)
	private Byte status;
	
	@NotNull(message = "Field [person] is required")
	@ManyToOne(optional = false, cascade = { CascadeType.PERSIST },
		fetch = FetchType.LAZY)
	@JoinColumn(name = "person_id", nullable = false,
		foreignKey = @ForeignKey(name = "fk_order__person_id"))
	private Person person;
	
	@NotNull(message = "Order must have one or more items")
	@OneToMany(mappedBy = "order", fetch = FetchType.EAGER, cascade = {
		CascadeType.REMOVE, CascadeType.PERSIST, CascadeType.MERGE })
	private List<OrderItem> orderitems = new ArrayList<>();
	
	@OneToOne(mappedBy = "order", fetch = FetchType.LAZY,
		cascade = { CascadeType.REMOVE, CascadeType.PERSIST })
	private Payment payment;
	
	@Embedded
	private Address deliveryAddress;
	
	public OrderStatus getStatus() {
		return OrderStatus.toEnum(this.status);
	}
	
	public void setStatus(OrderStatus status) {
		this.status = status.getCode();
	}
	
	public boolean isPaid() {
		return OrderStatus.PAID.getCode().equals(this.status);
	}
	
	public boolean isWaitting() {
		return OrderStatus.WAITING.getCode().equals(this.status);
	}
	
	public boolean isCanceled() {
		return OrderStatus.CANCELED.getCode().equals(this.status);
	}
	
	public void setTotal() {
		this.total = calcTotal(this.orderitems);
	}
	
	public BigDecimal calcTotal(List<OrderItem> orderitems) {
		if (!orderitems.isEmpty()) {
			return orderitems.stream().map(OrderItem::calcSubTotal)
				.reduce(BigDecimal.ZERO, BigDecimal::add);
		}
		return BigDecimal.ZERO;
	}
	
	public BigDecimal calcTotal() {
		if (!this.orderitems.isEmpty()) {
			return this.orderitems.stream().map(OrderItem::calcSubTotal)
				.reduce(BigDecimal.ZERO, BigDecimal::add);
		}
		return BigDecimal.ZERO;
	}
	
	public void calculeTotal() {
		if (this.orderitems != null) {
			this.total = (BigDecimal) this.orderitems.stream()
				.map(i -> BigDecimal.valueOf(i.getQuantity())
					.multiply(i.getProduct().getUnitPrice()));
		} else {
			this.total = BigDecimal.ZERO;
		}
	}
	
	@PrePersist
	private void orderPersist() {
		setTotal();
	}
	
	@PreUpdate
	private void orderUpdate() {
		if (!isPaid()) {
			setTotal();
		}
	}
	
}
