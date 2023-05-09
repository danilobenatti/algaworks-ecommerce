package com.algaworks.ecommerce.util;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

import org.apache.commons.io.FileUtils;

import com.algaworks.ecommerce.exception.FileNotFoundException;
import com.algaworks.ecommerce.model.enums.ImageExtension;

public class UpLoadFiles {
	
	private UpLoadFiles() {
	}
	
	public static byte[] getByteArrayFromFile(File file) {
		try {
			return FileUtils.readFileToByteArray(file);
		} catch (IOException ex) {
			throw new FileNotFoundException("File not found.", ex);
		}
	}
	
	public static ImageExtension validFileExtension(File file) {
		Optional<String> extension = Optional.ofNullable(file.getName())
			.filter(f -> f.contains("."))
			.map(f -> f.substring(file.getName().lastIndexOf(".") + 1));
		if (extension.isPresent()) {
			for (ImageExtension ext : ImageExtension.values()) {
				if (ext.getValue().equalsIgnoreCase(extension.get())) {
					return ext;
				}
			}
		}
		return null;
	}
	
}
