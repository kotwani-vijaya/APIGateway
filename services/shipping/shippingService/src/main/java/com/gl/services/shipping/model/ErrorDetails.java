package com.gl.services.shipping.model;

public class ErrorDetails {

	String message;
	int code;
	
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	
	public ErrorDetails() {
		// TODO Auto-generated constructor stub
	}
	public ErrorDetails(String message, int code) {
		super();
	
		this.message = message;
		this.code = code;
	}
	
	
}
