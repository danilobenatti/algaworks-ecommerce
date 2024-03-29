package com.algaworks.ecommerce.model;

import java.io.IOException;
import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.util.Date;

import com.algaworks.ecommerce.exception.FileNotFoundException;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
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
	
	@NotNull
	@OneToOne(optional = false, fetch = FetchType.LAZY,
		cascade = CascadeType.REMOVE)
	@MapsId
	@JoinColumn(name = "order_id", nullable = false,
		foreignKey = @ForeignKey(name = "fk_invoice__order_id"))
	private Order order;
	
	@NotEmpty
	@Lob
//	@Column(name = "col_xml", columnDefinition = "BLOB")
	@Column(name = "col_xml", length = 10485760) // 10485760 Bytes = 10MB
	private byte[] xml;
	
	@PastOrPresent
	@NotNull
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
	
	@Override
	public String toString() {
		return this.xml != null ? new String(this.xml, StandardCharsets.UTF_8)
			: "N/D";
	}
	
}
