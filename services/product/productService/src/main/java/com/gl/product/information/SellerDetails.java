package com.gl.product.information;

public class SellerDetails {
	private String name, verified,ratings, ratingsFrom;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getVerified() {
		return verified;
	}

	public void setVerified(String verified) {
		this.verified = verified;
	}

	public String getRatings() {
		return ratings;
	}

	public void setRatings(String ratings) {
		this.ratings = ratings;
	}

	public String getRatingsFrom() {
		return ratingsFrom;
	}

	public void setRatingsFrom(String ratingsFrom) {
		this.ratingsFrom = ratingsFrom;
	}

	public SellerDetails(String name, String verified, String ratings,
			String ratingsFrom) {
		super();
		this.name = name;
		this.verified = verified;
		this.ratings = ratings;
		this.ratingsFrom = ratingsFrom;
	}

	public SellerDetails() {
	}
}
