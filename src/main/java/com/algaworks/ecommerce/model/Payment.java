package com.algaworks.ecommerce.model;

import java.io.Serializable;

import com.algaworks.ecommerce.model.enums.PaymentStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
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
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "tbl_payments")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Payment extends BaseEntityLong implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@OneToOne(optional = false)
	@JoinColumn(name = "order_id",
		foreignKey = @ForeignKey(name = "fk_payments_order_id"))
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
