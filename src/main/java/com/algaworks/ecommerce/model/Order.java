package com.algaworks.ecommerce.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.algaworks.ecommerce.model.enums.OrderStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
@Entity
@Table(name = "tbl_orders")
public class Order implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@EqualsAndHashCode.Include
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_order")
	private Long id;
	
	@Column(name = "col_order_date")
	private LocalDateTime orderDate;
	
	@Column(name = "col_executiondate")
	private LocalDateTime executionDate;
	
	@Column(name = "id_invoice")
	private Invoice invoice;
	
	@Column(name = "col_total")
	private BigDecimal total;
	
	@Column(name = "col_status")
	private Byte status;
	
	@Embedded
	private Address deliveryAddress;
	
	public OrderStatus getStatus() {
		return OrderStatus.toEnum(this.status);
	}
	
	public void setStatus(OrderStatus status) {
		this.status = status.getCode();
	}
}
