package com.algaworks.ecommerce.model;

import java.io.Serializable;

import com.algaworks.ecommerce.model.enums.ImageExtension;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class Image implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Column(name = "col_filename", length = 100, nullable = false)
	private String filename;
	
	@Column(name = "blob_file", length = 5242880 /* Bytes */, nullable = false)
	private byte[] file;
	
	@Column(name = "col_extension")
	private ImageExtension extension;
	
}
