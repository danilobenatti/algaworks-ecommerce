package com.algaworks.ecommerce.model;

import java.time.LocalDate;

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
@Table(name = "tbl_payments_bankslip")
@PrimaryKeyJoinColumn(name = "id")
public class PaymentBankSlip extends Payment {
	private static final long serialVersionUID = 1L;
	
	@Column(name = "col_expirationdate")
	private LocalDate expirationDate;
	
	@Column(name = "col_payday")
	private LocalDate payDay;
	
	public PaymentBankSlip(Long id, Order order, Byte status,
			LocalDate expirationDate, LocalDate payDay) {
		super(id, order, status);
		this.expirationDate = expirationDate;
		this.payDay = payDay;
	}
	
}
