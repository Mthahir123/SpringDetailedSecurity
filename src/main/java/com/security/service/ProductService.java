package com.security.service;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.stereotype.Service;

import com.security.service.entity.Product;

import jakarta.annotation.PostConstruct;

@Service
public class ProductService {

	List<Product> productList = null;

	@PostConstruct
	public void loadProductsFromDB() {
		productList = IntStream.rangeClosed(1, 100)
				.mapToObj(i -> new Product(i, "Product " + i, new Random().nextInt(10), new Random().nextInt(5000)))
				.collect(Collectors.toList());
	}

	public List<Product> getProducts() {
		return productList;
	}

	public Product getProduct(int id) {
		return productList.stream().filter(product -> product.getId() == id).findAny()
				.orElseThrow(() -> new RuntimeException("Product " + id + " not found"));

	}
}
