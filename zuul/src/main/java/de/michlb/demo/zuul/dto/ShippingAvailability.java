package de.michlb.demo.zuul.dto;

public class ShippingAvailability {

	String shippingAvailable;
	
	ShippingDetails shippingDetails;

	public String getShippingAvailable() {
		return shippingAvailable;
	}

	public void setShippingAvailable(String shippingAvailable) {
		this.shippingAvailable = shippingAvailable;
	}
	
		

	public ShippingDetails getShippingDetails() {
		return shippingDetails;
	}

	public void setShippingDetails(ShippingDetails shippingDetails) {
		this.shippingDetails = shippingDetails;
	}

	public ShippingAvailability(String shippingAvailable, ShippingDetails shippingDetails) {
		super();
		this.shippingAvailable = shippingAvailable;
		
		this.shippingDetails = shippingDetails;
	}
	
	public ShippingAvailability() {
		// TODO Auto-generated constructor stub
	}
	
}
