package com.gl.product.information;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.LoggerContext;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

@RestController
public class ProductController {
	static SellerDetails sellerDetails = null;
	static ProductDetails productDetails = null;
	private static File[] listOfFiles;
	private static String location;
	private static LoggerContext context = (LoggerContext) LogManager.getContext(false);
	private static final Logger LOGGER = LogManager
			.getLogger(ProductController.class.getName());

	@RequestMapping(value = "products/{ProductId}", method = RequestMethod.GET)
	public ResponseEntity<ProductDetails> getProduct(
			@PathVariable String ProductId) throws ProductNotFoundException {
			LOGGER.info("Request logged with Request Id as : " + ProductId);
		String product = "Product-" + ProductId;
		ProductDetails productDetails = new ProductDetails();
		if (ProductId.equals("") || ProductId.equals("\t") || ProductId.equals("\n")) {
			LOGGER.info("Response logged with empty Request Id");
			return new ResponseEntity<ProductDetails>(productDetails,
					HttpStatus.BAD_REQUEST);
		}
		
		int flag = 0;

		try {
			for (int i = 0; i < listOfFiles.length; i++) {

				if (listOfFiles[i].isFile()) {

					if (listOfFiles[i].getName().equals(product + ".txt")) {
						ProductDetails p = readJsonFile(location + product
								+ ".txt");
						ObjectWriter ow = new ObjectMapper().writer()
								.withDefaultPrettyPrinter();
						String json = ow.writeValueAsString(p);
						LOGGER.info("Response logged with product as : \n"
								+ json);
						flag = 1;
						return new ResponseEntity<ProductDetails>(p,
								HttpStatus.OK);
					}

				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (flag == 0) {
			LOGGER.info("Response logged with product not found");
			throw new ProductNotFoundException("1244", "Invalid Product Id");
		}
		ProductDetails empty = new ProductDetails();
		return new ResponseEntity<ProductDetails>(empty, HttpStatus.OK);
	}

	@SuppressWarnings("unchecked")
	public static ProductDetails readJsonFile(String path) throws IOException {
		JSONParser parser = new JSONParser();
		try {
			Object obj = parser.parse(new FileReader(path));
			JSONObject jsonObject = (JSONObject) obj;			
			String name = (String) jsonObject.get("Name");
			JSONArray description = (JSONArray) jsonObject.get("Description");
			String cost = (String) jsonObject.get("Cost");
			String discount = (String) jsonObject.get("Discount");
			String inStock = (String) jsonObject.get("InStock");
			String rating = (String) jsonObject.get("Rating");
			String cashOnDelivery = (String) jsonObject.get("CashOnDelivery");
			String imageUrl = (String) jsonObject.get("ImageURL");
			JSONObject sellerObject = (JSONObject) jsonObject.get("Seller");
			String sellerName = (String) sellerObject.get("Name");
			String ratings = (String) sellerObject.get("Ratings");
			String ratingsFrom = (String) sellerObject.get("RatingsFrom");
			String verification = (String) sellerObject.get("Verified");
			sellerDetails = new SellerDetails(sellerName, verification,
					ratings, ratingsFrom);
			productDetails = new ProductDetails(name, cost, discount, inStock,
					rating, cashOnDelivery, imageUrl, description,
					sellerDetails);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return productDetails;
	}

	public void setFiles(File[] productFiles) {
		listOfFiles = productFiles;
	}
	
	public void setFileLocation(String loc)
	{
		location=loc;
	}
	
	public void setLogProperties(String logPropPath)
	{
		File file = new File(logPropPath);
		context.setConfigLocation(file.toURI());
	}
}
