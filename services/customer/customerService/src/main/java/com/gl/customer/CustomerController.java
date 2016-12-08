package com.gl.customer;

import java.io.File;
import java.io.FileReader;

import org.apache.log4j.Logger;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

@RestController
public class CustomerController {
	private static File[] listOfFiles;
	private static String location;
	private static final Logger LOGGER = Logger.getLogger(CustomerController.class.getName());

	@RequestMapping(value = "/details/{CustomerId}", method = RequestMethod.GET)
	public ResponseEntity<CustomerDetails> getCustomerDetails(
			@PathVariable("CustomerId") String id, @RequestHeader("CID") String cid) throws CustomerNotFoundException {
		LOGGER.info("Request received with CID : " + cid +" for customerId : " + id);
		if (id.equals("") || id.equals("\t") || id.equals("\n")) {
			CustomerDetails cd = new CustomerDetails();
			LOGGER.info("Response logged with empty Request Id");
			return new ResponseEntity<CustomerDetails>(cd,
					HttpStatus.BAD_REQUEST);
		}
		String CustomerId = "Customer-" + id;

		int flag = 0;

		try {
			for (int i = 0; i < listOfFiles.length; i++) {

				if (listOfFiles[i].isFile()) {

					if (listOfFiles[i].getName().equals(CustomerId + ".txt")) {
						CustomerDetails custDet = readJsonFile(location
								+ CustomerId + ".txt");
						ObjectWriter ow = new ObjectMapper().writer()
								.withDefaultPrettyPrinter();
						String json = ow.writeValueAsString(custDet);
						LOGGER.info("Response returned for CID : " + cid +" with customerId :" + id + " is : \n"+ json);						
						flag = 1;
						return new ResponseEntity<CustomerDetails>(custDet,
								HttpStatus.OK);
					}

				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (flag == 0) {
			LOGGER.info("Response returned for CID : " + cid +" is product not found");
			throw new CustomerNotFoundException("1244", "Invalid Product Id");
		}
		CustomerDetails empty = new CustomerDetails();
		return new ResponseEntity<CustomerDetails>(empty, HttpStatus.OK);
	}

	public static CustomerDetails readJsonFile(String path) {
		CustomerDetails customerDetails = null;
		JSONParser parser = new JSONParser();
		try {
			Object obj = parser.parse(new FileReader(path));
			JSONObject jsonObject = (JSONObject) obj;
			String name = (String) jsonObject.get("Name");
			String zip = (String) jsonObject.get("Zip");
			String city = (String) jsonObject.get("City");
			String isFirstPurchase = (String) jsonObject.get("IsFirstPurchase");
			customerDetails = new CustomerDetails(name, zip, city, isFirstPurchase);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return customerDetails;
	}

	public void setFiles(File[] productFiles) {
		listOfFiles = productFiles;
	}

	public void setFileLocation(String loc) {
		location = loc;
	}

	
}
