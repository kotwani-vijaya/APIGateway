package com.gl.inventory;

import java.util.ArrayList;
import java.util.List;

public class Inventory {
	private List<Integer> productId = new ArrayList<Integer>();

	public List<Integer> getProductId() {
		return productId;
	}

	public void setProductId(List<Integer> productId) {
		this.productId = productId;
	}

	public Inventory(List<Integer> productId) {
		super();
		this.productId = productId;
	}
	
	public Inventory(){}

}
