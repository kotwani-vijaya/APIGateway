package com.gl.product.information;

import java.util.ArrayList;
import java.util.List;

public class ProductDetails {
	private String name, cost, discount, inStock, rating, cashOnDelivery,
			imageUrl;
	private double apiVersion;
	private List<String> description = new ArrayList<String>();
	private SellerDetails sellerDetails;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCost() {
		return cost;
	}

	public void setCost(String cost) {
		this.cost = cost;
	}

	public String getDiscount() {
		return discount;
	}

	public void setDiscount(String discount) {
		this.discount = discount;
	}

	public String getInStock() {
		return inStock;
	}

	public void setInStock(String inStock) {
		this.inStock = inStock;
	}

	public String getRating() {
		return rating;
	}

	public void setRating(String rating) {
		this.rating = rating;
	}

	public String getCashOnDelivery() {
		return cashOnDelivery;
	}

	public void setCashOnDelivery(String cashOnDelivery) {
		this.cashOnDelivery = cashOnDelivery;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public double getApiVersion() {
		return apiVersion;
	}

	public void setApiVersion(double apiVersion) {
		this.apiVersion = apiVersion;
	}

	public List<String> getDescription() {
		return description;
	}

	public void setDescription(List<String> description) {
		this.description = description;
	}

	public SellerDetails getSellerDetails() {
		return sellerDetails;
	}

	public void setSellerDetails(SellerDetails sellerDetails) {
		this.sellerDetails = sellerDetails;
	}

	public ProductDetails(String name, String cost, String discount,
			String inStock, String rating, String cashOnDelivery,
			String imageUrl, double apiVersion, List<String> description,
			SellerDetails sellerDetails) {
		super();
		this.name = name;
		this.cost = cost;
		this.discount = discount;
		this.inStock = inStock;
		this.rating = rating;
		this.cashOnDelivery = cashOnDelivery;
		this.imageUrl = imageUrl;
		this.apiVersion = apiVersion;
		this.description = description;
		this.sellerDetails = sellerDetails;
	}

	public ProductDetails() {
	}

}
