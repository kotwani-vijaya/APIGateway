package de.michlb.demo.zuul.dto;

public class AggregatedResult {
	
	private Customer customer;
	
	private Product product;
	
	private Recommendations recommendations;
	
	private ShippingAvailability shippingAvailability;

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public Recommendations getRecommendations() {
		return recommendations;
	}

	public void setRecommendations(Recommendations recommendations) {
		this.recommendations = recommendations;
	}

	public ShippingAvailability getShippingAvailability() {
		return shippingAvailability;
	}

	public void setShippingAvailability(ShippingAvailability shippingAvailability) {
		this.shippingAvailability = shippingAvailability;
	}
	
	

}
