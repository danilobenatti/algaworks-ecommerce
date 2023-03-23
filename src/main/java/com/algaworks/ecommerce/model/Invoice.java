package com.algaworks.ecommerce.model;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrePersist;
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
public class Invoice implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@EqualsAndHashCode.Include
	@Column(name = "order_id")
	private Long id;
	
	@MapsId
	@OneToOne(optional = false)
	@JoinColumn(name = "order_id",
		foreignKey = @ForeignKey(name = "fk_invoice_order_id"))
	private Order order;
	
	@Lob
	@Column(name = "col_xml", columnDefinition = "BLOB", length = 20971520)
	private byte[] xml;
	
	@Column(name = "col_issue_date")
	private LocalDateTime issueDate;
	
	@PrePersist
	public void onPersist() {
		this.issueDate = LocalDateTime.now();
	}
	
	public static byte[] uploadInvoice() {
		try {
			return Invoice.class.getResourceAsStream("NFe_assinada.xml")
					.readAllBytes();
		} catch (IOException ex) {
			throw new RuntimeException("File not found.", ex);
		}
		
	}
	
}
