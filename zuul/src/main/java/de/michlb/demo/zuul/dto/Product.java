package de.michlb.demo.zuul.dto;

import java.util.List;

public class Product {
	
	private String name;
	
	private List<String> description;
	
	private String cost;
	
	private String discount;
	
	private String rating;
	
	private String cashOnDelivery;
	
	private String imageUrl;
	
	private Seller sellerDetails;
	
	private String inStock;
	
	private String qty;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<String> getDescription() {
		return description;
	}

	public void setDescription(List<String> description) {
		this.description = description;
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

	
	public Seller getSellerDetails() {
		return sellerDetails;
	}

	public void setSellerDetails(Seller sellerDetails) {
		this.sellerDetails = sellerDetails;
	}

	public String getInStock() {
		return inStock;
	}

	public void setInStock(String inStock) {
		this.inStock = inStock;
	}

	public String getQty() {
		return qty;
	}

	public void setQty(String qty) {
		this.qty = qty;
	}
	
	
	

}
