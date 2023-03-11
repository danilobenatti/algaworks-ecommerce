package com.algaworks.ecommerce.model;

import java.io.Serializable;
import java.time.LocalDateTime;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
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
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "tbl_invoices")
//	uniqueConstraints = @UniqueConstraint(name = "uk_invoice_order_id",
//		columnNames = "oreder_id"))
public class Invoice implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@EqualsAndHashCode.Include
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@OneToOne(cascade = CascadeType.REMOVE)
//	@JoinColumn(name = "oreder_id",
//		foreignKey = @ForeignKey(name = "fk_invoice_order_id"))
	@JoinTable(name = "tbl_order_invoice",
		joinColumns = @JoinColumn(name = "invoice_id", unique = true,
			foreignKey = @ForeignKey(name = "fk_orderinvoice__invoice_id")),
		inverseJoinColumns = @JoinColumn(name = "order_id", unique = true,
			foreignKey = @ForeignKey(name = "fk_orderinvoice__order_id")))
	private Order order;
	
	@Column(name = "col_xml")
	private String xml;
	
	@Column(name = "col_issue_date")
	private LocalDateTime issueDate;
}
