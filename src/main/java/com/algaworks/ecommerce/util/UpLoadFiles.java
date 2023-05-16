package com.algaworks.ecommerce.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.apache.commons.io.FileUtils;

import com.algaworks.ecommerce.exception.FileNotFoundException;
import com.algaworks.ecommerce.model.Product;
import com.algaworks.ecommerce.model.enums.ImageExtension;
import com.algaworks.ecommerce.model.enums.ProductUnit;

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
	
	public static ImageExtension getImageExtension(File file) {
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
	
	public static List<String> getStreamFromFile(File file) {
		try (BufferedReader reader = Files.newBufferedReader(file.toPath(),
			StandardCharsets.UTF_8)) {
			return reader.lines().toList();
		} catch (IOException ex) {
			throw new FileNotFoundException("File not found.", ex);
		}
	}
	
	public static List<Product> getProductsFromFile(File file) {
		List<String> stream = getStreamFromFile(file);
		List<Product> list = new ArrayList<>();
		if (stream != null) {
			for (String line : stream) {
				String[] index = line.split(";");
				Product product = new Product();
				product.setName(index[0]);
				product.setDescription(index[1]);
				product.setUnitPrice(new BigDecimal(index[2]));
				product.setUnit(ProductUnit.UNITY); // standard value
				list.add(product);
			}
			return list;
		}
		return Collections.emptyList();
	}
	
}
