package com.gl.services.product.controller;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.gl.services.product.error.response.ErrorMessage;
import com.gl.services.product.error.response.ProductNotFoundException;
import com.gl.services.product.model.ProductDetails;
import com.gl.services.product.model.SellerDetails;
import com.gl.services.product.util.JsonUtil;

@RestController
public class ProductController {
	static SellerDetails sellerDetails = null;
	static ProductDetails productDetails = new ProductDetails();

	@Value("${FilePath}")
	private String location;

	private static final Logger LOGGER = Logger
			.getLogger(ProductController.class.getName());

	@RequestMapping(value = "/products/{ProductId}", method = RequestMethod.GET)
	public ResponseEntity<ProductDetails> getProduct(
			@PathVariable String ProductId, @RequestHeader("CID") String cid)
			throws ProductNotFoundException, IOException {
		LOGGER.info("Request received with CID : " + cid + " for productId :"
				+ ProductId);
		String product = "Product-" + ProductId;
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.add("Access-Control-Allow-Origin", "localhost");
		responseHeaders.add("Access-Control-Allow-Methods",
				"GET, POST, PATCH, PUT, DELETE, OPTIONS");
		responseHeaders.add("Access-Control-Allow-Header", "Content-Type");
		if (ProductId == null || ProductId.isEmpty()) {
			LOGGER.info("Response logged with empty Request Id");
			throw new ProductNotFoundException("Product Id is empty");
		}

		ProductDetails p = readJsonFile(location + product + ".txt", cid);

		if (p != null) {
			return new ResponseEntity<ProductDetails>(p, responseHeaders,
					HttpStatus.OK);
		} else {

			LOGGER.info("Response returned for CID : " + cid
					+ " is : product not found");
			throw new ProductNotFoundException("Invalid Product Id");
		}
	}

	public static ProductDetails readJsonFile(String path, String cid)
			throws ProductNotFoundException, JsonParseException, JsonMappingException, IOException {
		String line;
		StringBuilder out;
		try {

			InputStream inputStream = new FileInputStream(path);

			BufferedReader reader = new BufferedReader(new InputStreamReader(
					inputStream));
			out = new StringBuilder();
			while ((line = reader.readLine()) != null) {
				out.append(line);
			}
			reader.close();
			productDetails = JsonUtil.toObject(out.toString(), ProductDetails.class);
		} catch (Exception e) {
			LOGGER.info("Response returned for CID : " + cid
					+ " is : product not found");
			throw new ProductNotFoundException("Invalid Product Id");
		}
		return productDetails;
	}

	@ExceptionHandler(ProductNotFoundException.class)
	public ResponseEntity<ErrorMessage> throwErrorMessage(
			ProductNotFoundException customException) {
		ErrorMessage error = new ErrorMessage(1244, "Invalid Product Id");
		return new ResponseEntity<ErrorMessage>(error, HttpStatus.OK);
	}
}
