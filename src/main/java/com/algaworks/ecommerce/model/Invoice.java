package com.algaworks.ecommerce.model;

import java.io.IOException;
import java.io.Serializable;
import java.util.Date;

import com.algaworks.ecommerce.exception.FileNotFoundException;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
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
	private Long id;
	
	@OneToOne
	@MapsId
	@JoinColumn(name = "order_id", nullable = false,
		foreignKey = @ForeignKey(name = "fk_invoice__order_id"))
	private Order order;
	
	@Lob
//	@Column(name = "col_xml", columnDefinition = "BLOB")
	@Column(name = "col_xml", length = 10485760) // 10485760 Bytes = 10MB
	private byte[] xml;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "col_issuedatetime", nullable = false)
	private Date issuedatetime;
	
	public static byte[] uploadInvoice() {
		try {
			return Invoice.class.getResourceAsStream("NFe_assinada.xml")
				.readAllBytes();
		} catch (IOException ex) {
			throw new FileNotFoundException("File not found.", ex);
		}
		
	}
	
}
