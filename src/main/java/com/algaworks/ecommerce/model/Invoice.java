package com.algaworks.ecommerce.model;

import java.io.IOException;
import java.io.Serializable;

import com.algaworks.ecommerce.exception.FileNotFoundException;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
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
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "tbl_invoices")
public class Invoice extends BaseEntityLong implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@MapsId
	@OneToOne(optional = false)
	@JoinColumn(name = "order_id",
		foreignKey = @ForeignKey(name = "fk_invoice_order_id"))
	private Order order;
	
	@Lob
//	@Column(name = "col_xml", columnDefinition = "BLOB")
	@Column(name = "col_xml", length = 10485760) // 10485760 Bytes = 10MB
	private byte[] xml;
	
	public static byte[] uploadInvoice() {
		try {
			return Invoice.class.getResourceAsStream("NFe_assinada.xml")
				.readAllBytes();
		} catch (IOException ex) {
			throw new FileNotFoundException("File not found.", ex);
		}
		
	}
	
}
