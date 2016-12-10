package com.gl.productShipping;

import java.util.List;

public class ProductDetails {

	public String productId;

	public List<String> shippingLocations;

	// *************************************************************************************

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	// ****************************************************************************************

	
	
	public ProductDetails() {
		// TODO Auto-generated constructor stub
	}

	public List<String> getShippingLocations() {
		return shippingLocations;
	}

	public void setShippingLocations(List<String> shippingLocations) {
		this.shippingLocations = shippingLocations;
	}

	public ProductDetails(String productId, List<String> shippingLocations) {
		super();
		this.productId = productId;
		this.shippingLocations = shippingLocations;
	}
	
	
}
