package com.cosmo.my_auth_service.dto;


import com.cosmo.my_auth_service.entities.Product;

import java.math.BigDecimal;

public class ProductDTO {

	private Long id;
	private String name;
	private BigDecimal price;
	
	public ProductDTO() {
	}

	public ProductDTO(Long id, String name, BigDecimal price) {
		this.id = id;
		this.name = name;
		this.price = price;
	}

	public ProductDTO(Product entity) {
		id = entity.getId();
		name = entity.getName();
		price = entity.getPrice();
	}

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public BigDecimal getPrice() {
		return price;
	}
}
