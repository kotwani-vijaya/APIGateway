package com.gl.services.shipping.model;

public class CustomerDetails {

	String customerId;
	String ZIP;

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public String getZIP() {
		return ZIP;
	}

	public void setZIP(String zIP) {
		ZIP = zIP;
	}

	public CustomerDetails(String customerId, String zIP) {
		super();
		this.customerId = customerId;
		ZIP = zIP;
	}

	CustomerDetails() {

	}

}
