package com.algaworks.ecommerce.model;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "tbl_payments_bankslip")
@PrimaryKeyJoinColumn(name = "order_id",
	foreignKey = @ForeignKey(name = "fk_paymentsbankslip__order_id"))
@DiscriminatorValue(value = "0")
public class PaymentBankSlip extends Payment {
	private static final long serialVersionUID = 1L;
	
	@NotNull
	@FutureOrPresent
	@Column(name = "col_expirationdate")
	private LocalDate expirationDate;
	
	@PastOrPresent
	@Column(name = "col_payday")
	private LocalDate payDay;
	
	public PaymentBankSlip(Order order, Byte status, LocalDate expirationDate,
		LocalDate payDay) {
		super(order, status);
		this.expirationDate = expirationDate;
		this.payDay = payDay;
	}
	
}
