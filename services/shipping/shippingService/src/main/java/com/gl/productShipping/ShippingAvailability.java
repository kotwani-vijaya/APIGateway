package com.gl.productShipping;

public class ShippingAvailability {

	String shippingAvailable,ZIP,city,deliveryTime;

	public String getShippingAvailable() {
		return shippingAvailable;
	}

	public void setShippingAvailable(String shippingAvailable) {
		this.shippingAvailable = shippingAvailable;
	}

	public String getZIP() {
		return ZIP;
	}

	public void setZIP(String zIP) {
		ZIP = zIP;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getDeliveryTime() {
		return deliveryTime;
	}

	public void setDeliveryTime(String deliveryTime) {
		this.deliveryTime = deliveryTime;
	}

	public ShippingAvailability(String shippingAvailable, String zIP,
			String city, String deliveryTime) {
		super();
		this.shippingAvailable = shippingAvailable;
		ZIP = zIP;
		this.city = city;
		this.deliveryTime = deliveryTime;
	}
	
	public ShippingAvailability() {
		// TODO Auto-generated constructor stub
	}
	
}
