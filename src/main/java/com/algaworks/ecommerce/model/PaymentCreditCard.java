package com.algaworks.ecommerce.model;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
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
@PrimaryKeyJoinColumn(name = "order_id",
	foreignKey = @ForeignKey(name = "fk_paymentscreditcard__order_id"))
@DiscriminatorValue(value = "1")
public class PaymentCreditCard extends Payment {
	private static final long serialVersionUID = 1L;
	
	@NotNull
	@Column(name = "col_number_installments")
	private Integer numberOfInstallments;
	
	public PaymentCreditCard(Order order, Byte status,
		Integer numberOfInstallments) {
		super(order, status);
		this.numberOfInstallments = numberOfInstallments;
	}
	
}
