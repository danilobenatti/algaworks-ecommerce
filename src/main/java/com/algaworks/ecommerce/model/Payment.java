package com.algaworks.ecommerce.model;

import java.io.Serializable;

import com.algaworks.ecommerce.model.enums.PaymentStatus;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.DiscriminatorType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
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
@EqualsAndHashCode(callSuper = true, exclude = "order")
@Entity
@Table(name = "tbl_payments")
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "col_payment_type",
	discriminatorType = DiscriminatorType.INTEGER,
	columnDefinition = "TINYINT(1)")
public abstract class Payment extends BaseEntityLong implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@MapsId
	@OneToOne(optional = false, fetch = FetchType.LAZY,
		cascade = CascadeType.REMOVE)
	@JoinColumn(name = "order_id", nullable = false,
		foreignKey = @ForeignKey(name = "fk_payments__order_id"))
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
