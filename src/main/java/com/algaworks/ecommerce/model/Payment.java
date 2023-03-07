package com.algaworks.ecommerce.model;

import java.io.Serializable;

import com.algaworks.ecommerce.model.enums.PaymentStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
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
@Table(name = "tbl_payments")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Payment implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@EqualsAndHashCode.Include
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_payment")
	private Long id;
	
	@Column(name = "id_order")
	private Order order;
	
	@Column(name = "col_status")
	private Byte status;
	
	public PaymentStatus getStatus() {
		return PaymentStatus.toEnum(this.status);
	}
	
	public void setStatus(PaymentStatus status) {
		this.status = status.getCode();
	}
	
}
