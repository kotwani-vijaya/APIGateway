package com.gl.services.customer.model;

public class CustomerDetails {
	private String name, zip, city, isFirstPurchase;

	public CustomerDetails(String name, String zip,
			String city, String isFirstPurchase) {
		super();
		this.name = name;
		this.zip = zip;
		this.city = city;
		this.isFirstPurchase = isFirstPurchase;
	}
	
	public CustomerDetails(){}

	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getZip() {
		return zip;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getIsFirstPurchase() {
		return isFirstPurchase;
	}

	public void setIsFirstPurchase(String isFirstPurchase) {
		this.isFirstPurchase = isFirstPurchase;
	}
}
