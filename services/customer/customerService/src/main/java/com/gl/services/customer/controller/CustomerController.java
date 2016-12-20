package com.gl.services.customer.controller;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.gl.services.customer.error.response.ErrorMessage;
import com.gl.services.customer.error.response.ProductNotFoundException;
import com.gl.services.customer.model.CustomerDetails;
import com.gl.services.customer.util.JsonUtil;

@RestController
public class CustomerController {
	@Value("${FilePath}")
	private String location;
	private static final Logger LOGGER = Logger
			.getLogger(CustomerController.class.getName());

	@RequestMapping(value = "/details/{CustomerId}", method = RequestMethod.GET)
	public ResponseEntity<CustomerDetails> getCustomerDetails(
			@PathVariable("CustomerId") String id,
			@RequestHeader("CID") String cid) throws ProductNotFoundException,
			IOException {
		LOGGER.info("Request received with CID : " + cid + " for customerId : "
				+ id);
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.add("Access-Control-Allow-Origin", "localhost");
		responseHeaders.add("Access-Control-Allow-Methods",
				"GET, POST, PATCH, PUT, DELETE, OPTIONS");
		responseHeaders.add("Access-Control-Allow-Header", "Content-Type");
		if (id == null || id.isEmpty()) {
			LOGGER.info("Response logged with empty Request Id");
			throw new ProductNotFoundException("Customer Id is empty");
		}
		String CustomerId = "Customer-" + id;
		CustomerDetails custDet = readJsonFile(location + CustomerId + ".txt",cid);
		if (custDet != null) {
			LOGGER.info("Response returned for CID : " + cid
					+ " with customerId :" + id);
			return new ResponseEntity<CustomerDetails>(custDet,
					responseHeaders, HttpStatus.OK);
		} else {

			LOGGER.info("Response returned for CID : " + cid
					+ " is : product not found");
			throw new ProductNotFoundException("Invalid Customer Id");
		}
	}

	public static CustomerDetails readJsonFile(String path, String cid) throws ProductNotFoundException {
		CustomerDetails customerDetails = null;
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
			customerDetails = JsonUtil.toObject(out.toString(), CustomerDetails.class);
		} catch (Exception e) {
			LOGGER.info("Response returned for CID : " + cid
					+ " is : product not found");
			throw new ProductNotFoundException("Invalid Customer Id");
		}
		return customerDetails;
	}

	@ExceptionHandler(ProductNotFoundException.class)
	public ResponseEntity<ErrorMessage> throwErrorMessage(
			ProductNotFoundException customException) {
		ErrorMessage error = new ErrorMessage(1244, "Invalid Customer Id");
		return new ResponseEntity<ErrorMessage>(error, HttpStatus.OK);
	}

}
