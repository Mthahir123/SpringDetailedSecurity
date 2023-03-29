package com.security.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.security.dao.AuthRequest;
import com.security.service.JwtService;
import com.security.service.ProductService;
import com.security.service.UserInfoUserDetailsService;
import com.security.service.entity.Product;
import com.security.service.entity.UserInfo;

@RestController
@RequestMapping("/products")
public class ProductController {

	@Autowired
	private ProductService service;

	@Autowired
	private UserInfoUserDetailsService userservice;

	@Autowired
	private JwtService jwtService;

	@Autowired
	private AuthenticationManager authenticationManager;

	@GetMapping("/welcome")
	public String welcome() {
		return "Welcome, This endpoint is not secured";
	}

	@PostMapping("/new")
	public UserInfo addNewUser(@RequestBody UserInfo userInfo) {
		return userservice.addUser(userInfo);
	}

	@PostMapping("/authenticate")
	public String authenticateAndGetToken(@RequestBody AuthRequest authRequest) {
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
		if (authentication.isAuthenticated())
			return jwtService.generateToken(authRequest.getUsername());
		throw new UsernameNotFoundException("Invalid User Request!");
	}

	@GetMapping("/all")
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	public List<Product> getAllProducts() {
		return service.getProducts();
	}

	@GetMapping("/{id}")
	@PreAuthorize("hasAuthority('ROLE_USER')")
	public Product getProductById(@PathVariable int id) {
		return service.getProduct(id);
	}
}
