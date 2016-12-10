package de.michlb.demo.zuul.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class Customer {
	
	private String name;
	
	private String city;
	
	private String zip;
	
	@JsonIgnore
	private String isFirstPurchase;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getZip() {
		return zip;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}

	public String getIsFirstPurchase() {
		return isFirstPurchase;
	}

	public void setIsFirstPurchase(String isFirstPurchase) {
		this.isFirstPurchase = isFirstPurchase;
	}
	
	
}
