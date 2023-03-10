package com.algaworks.ecommerce.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "tbl_payments_creditcard")
@PrimaryKeyJoinColumn(name = "id")
public class PaymentCreditCard extends Payment {
	private static final long serialVersionUID = 1L;
	
	@Column(name = "col_number_installments")
	private Integer numberOfInstallments;
	
	public PaymentCreditCard(Long id, Order order, Byte status,
			Integer numberOfInstallments) {
		super(id, order, status);
		this.numberOfInstallments = numberOfInstallments;
	}
	
}
