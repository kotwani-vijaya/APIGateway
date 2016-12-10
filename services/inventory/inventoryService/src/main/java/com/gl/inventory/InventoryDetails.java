package com.gl.inventory;

public class InventoryDetails {
	
	private String inStock;
	private long quantityAvailable;

	
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

	public InventoryDetails(String inStock,
			long quantityAvailable) {
		super();
		this.inStock = inStock;
		this.quantityAvailable = quantityAvailable;
	}

	public InventoryDetails() {
	}
}
