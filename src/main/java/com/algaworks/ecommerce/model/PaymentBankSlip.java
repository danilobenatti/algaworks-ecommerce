package com.algaworks.ecommerce.model;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@DiscriminatorValue(value = "1")
public class PaymentBankSlip extends Payment {
	private static final long serialVersionUID = 1L;
	
	@Column(name = "col_expirationdate")
	private LocalDate expirationDate;
	
	@Column(name = "col_payday")
	private LocalDate payDay;
	
	public PaymentBankSlip(Order order, Byte status, LocalDate expirationDate,
		LocalDate payDay) {
		super(order, status);
		this.expirationDate = expirationDate;
		this.payDay = payDay;
	}
	
}
