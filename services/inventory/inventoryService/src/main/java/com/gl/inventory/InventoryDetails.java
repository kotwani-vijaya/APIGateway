package com.gl.inventory;

public class InventoryDetails {
	private double apiVersion;
	private String inStock;
	private long quantityAvailable;

	public double getApiVersion() {
		return apiVersion;
	}

	public void setApiVersion(double apiVersion) {
		this.apiVersion = apiVersion;
	}

	public String getInStock() {
		return inStock;
	}

	public void setInStock(String inStock) {
		this.inStock = inStock;
	}

	public long getQuantityAvailable() {
		return quantityAvailable;
	}

	public void setQuantityAvailable(int quantityAvailable) {
		this.quantityAvailable = quantityAvailable;
	}

	public InventoryDetails(double apiVersion, String inStock,
			long quantityAvailable) {
		super();
		this.apiVersion = apiVersion;
		this.inStock = inStock;
		this.quantityAvailable = quantityAvailable;
	}

	public InventoryDetails() {
	}
}
