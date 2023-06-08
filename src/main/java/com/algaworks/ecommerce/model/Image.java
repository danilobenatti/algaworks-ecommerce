package com.algaworks.ecommerce.model;

import java.io.Serializable;

import com.algaworks.ecommerce.model.enums.ImageExtension;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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
	
	@NotBlank(message = "Filename is mandatory")
	@Size(max = 100, message = "Field [filename] must be max {max}")
	@Column(name = "col_filename", length = 100, nullable = false)
	private String filename;
	
	@NotNull(message = "File is mandatory")
	@Column(name = "blob_file", length = 5242880 /* 5242880 Bytes = 5MB */,
		nullable = false)
	private byte[] file;
	
	@NotBlank(message = "Extension is mandatory")
	@Column(name = "col_extension")
	private ImageExtension extension;
	
}
