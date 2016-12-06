package com.gl.productShipping;

public class ProductDetails {

	public String productId;

	public String shippingLocations;

	// *************************************************************************************

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	// ****************************************************************************************

	public String getShippingLocations() {
		return shippingLocations;
	}

	public void setShippingLocations(String shippingLocations) {
		this.shippingLocations = shippingLocations;
	}
	
	public ProductDetails() {
		// TODO Auto-generated constructor stub
	}

	public ProductDetails(String productId, String shippingCities) {
		super();
		this.productId = productId;
		this.shippingLocations = shippingCities;
	}
	
	
}
