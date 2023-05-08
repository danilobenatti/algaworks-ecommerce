package com.algaworks.ecommerce.model.enums;

import lombok.Getter;

@Getter
public enum ImageExtension {
	
	JPG((byte) 0, "jpg"), GIF((byte) 1, "gif"), PNG((byte) 2, "png"),
	BMP((byte) 3, "bmp"), SVG((byte) 4, "svg"), TIFF((byte) 5, "tiff");
	
	private Byte code;
	private String value;
	
	ImageExtension(Byte code, String value) {
		this.code = code;
		this.value = value;
	}
	
	public static ImageExtension toEnum(Byte code) {
		if (code == null)
			return null;
		for (ImageExtension extension : ImageExtension.values())
			if (code.equals(extension.getCode()))
				return extension;
		throw new IllegalArgumentException("Invalid code: " + code);
	}
	
}
