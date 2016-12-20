package com.gl.services.recommendation.main;

public class RecommendationNotFoundException extends Exception
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String message;
	private Integer code;

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public RecommendationNotFoundException(Integer code, String message) {
		super();
		this.code = code;
		this.message = message;
	}
	
}
