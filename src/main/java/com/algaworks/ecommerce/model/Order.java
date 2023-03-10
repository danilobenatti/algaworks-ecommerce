package com.algaworks.ecommerce.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.algaworks.ecommerce.listener.GenerateInvoiceListener;
import com.algaworks.ecommerce.listener.GenericListener;
import com.algaworks.ecommerce.model.enums.OrderStatus;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@EntityListeners({ GenerateInvoiceListener.class, GenericListener.class })
@Entity
@Table(name = "tbl_orders")
public class Order implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@EqualsAndHashCode.Include
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "col_order_date")
	private LocalDateTime orderDate;
	
	@Column(name = "col_order_dateupdate")
	private LocalDateTime orderDateUpdate;
	
	@Column(name = "col_execution_date")
	private LocalDateTime executionDate;
	
	@OneToOne(mappedBy = "order", cascade = CascadeType.REMOVE)
	private Invoice invoice;
	
	@Column(name = "col_total")
	private BigDecimal total;
	
	@Column(name = "col_status")
	private Byte status;
	
	@ManyToOne(optional = false)
	@JoinColumn(name = "person_id",
		foreignKey = @ForeignKey(name = "fk_order_person_id"))
	private Person person;
	
	@OneToMany(mappedBy = "order", fetch = FetchType.EAGER,
		cascade = CascadeType.REMOVE)
	private List<OrderItem> orderitems = new ArrayList<>();
	
	@OneToOne(mappedBy = "order", cascade = CascadeType.REMOVE)
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
		return OrderStatus.PAID.getCode().equals(status);
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
	
	@PrePersist
	public void onPersist() {
		setTotal();
		this.orderDate = LocalDateTime.now();
	}
	
	@PreUpdate
	public void onUpdate() {
		setTotal();
		this.orderDateUpdate = LocalDateTime.now();
	}
}
