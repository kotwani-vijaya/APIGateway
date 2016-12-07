package com.gl.productShipping;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.LoggerContext;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

@RestController
public class ShippingController implements ErrorController {

	public static ArrayList<ProductDetails> list = new ArrayList<ProductDetails>();

	public static ArrayList<CustomerDetails> customerList = new ArrayList<CustomerDetails>();

	public static ArrayList<ShippingAvailability> availableList = new ArrayList<ShippingAvailability>();

	public static ArrayList<ErrorDetails> errorList = new ArrayList<ErrorDetails>();
	
	private static LoggerContext context=(LoggerContext) LogManager.getContext(false);
	
	private static final Logger LOGGER = LogManager.getLogger(ShippingController.class.getName());

	ProductDetails details = null;
	
	//****************************************************************************************************
	
	public static void setLogProperties(String logPathProperties){
		File file=new File(logPathProperties);
		context.setConfigLocation(file.toURI());
	}

	// ***************************************************************************************************

	@RequestMapping(value = "/error", method = RequestMethod.GET)
	public String error(ModelMap model) {
		return "Error";
	}

	public String getErrorPath() {
		return "/error";
	}

	// ****************************************************************************************************

	@SuppressWarnings("unchecked")
	public static void shippingController() throws Exception {

		String shippingDetails = "templates/shippingDetails";

		ClassLoader classLoader = new ShippingController().getClass()
				.getClassLoader();

		InputStream inputStream = classLoader
				.getResourceAsStream(shippingDetails);

		BufferedReader reader = new BufferedReader(new InputStreamReader(
				inputStream));
		StringBuilder out = new StringBuilder();
		String line;
		while ((line = reader.readLine()) != null) {
			out.append(line);
		}
		reader.close();

		JSONParser parser = new JSONParser();

		Object obj = parser.parse(out.toString());

		JSONArray jsonArray = (JSONArray) obj;

		@SuppressWarnings("rawtypes")
		Iterator it = jsonArray.iterator();

		if (jsonArray != null) {

			while (it.hasNext()) {

				JSONObject json = (JSONObject) it.next();

				String pId = (String) json.get("productId").toString();

				System.out.println("productId" + pId);

				ArrayList<String> cities = (JSONArray) json
						.get("shippingLocations");
				Iterator<String> iterator = cities.iterator();
				while (iterator.hasNext()) {
					System.out.println(iterator.next());
				}
				StringBuilder sb = new StringBuilder();
				for (String s : cities) {
					sb.append(s);
					sb.append(" ");
				}

				// int shippingLocations=(Integer)
				// json.get("shippingLocations");

				String shippingCities = sb.toString();

				list.add(new ProductDetails(pId, shippingCities));
			}
		}

	}

	// *********************************************************************************

	@RequestMapping(value = "/findProduct", method = RequestMethod.GET)
	public ResponseEntity<ProductDetails> findProductByProductId(

	@RequestParam("productId1") String productId1) throws Exception {

		ProductDetails productDetails = new ProductDetails();

		int check = 0;

		if (productId1 != null) {
			try {
				for (int j = 0; j < list.size(); j++) {
					if (list.get(j).getProductId().equals(productId1)) {
						productDetails = list.get(j);
						ObjectWriter writer=new ObjectMapper().writer().withDefaultPrettyPrinter();
						String json=writer.writeValueAsString(productDetails);
						LOGGER.info("Logger information:\n"+json);
						check = 1;
						break;
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

			if (check == 1)
				return new ResponseEntity<ProductDetails>(productDetails,
						HttpStatus.OK);
			else
				return new ResponseEntity<ProductDetails>(productDetails,
						HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<ProductDetails>(productDetails,
				HttpStatus.BAD_REQUEST);
	}

	// ******************************************************************************
	public static void customerController() throws Exception {

		String customerDetails = "templates/customerDetails";

		ClassLoader classLoader = new ShippingController().getClass()
				.getClassLoader();

		InputStream inputStream = classLoader
				.getResourceAsStream(customerDetails);

		BufferedReader reader = new BufferedReader(new InputStreamReader(
				inputStream));
		StringBuilder out = new StringBuilder();
		String line;
		while ((line = reader.readLine()) != null) {
			out.append(line);
		}

		reader.close();

		JSONParser parser = new JSONParser();

		Object obj = parser.parse(out.toString());

		JSONArray jsonArray = (JSONArray) obj;

		@SuppressWarnings("rawtypes")
		Iterator it = jsonArray.iterator();

		if (jsonArray != null) {

			while (it.hasNext()) {

				JSONObject json = (JSONObject) it.next();

				String cId = (String) json.get("customerId").toString();

				System.out.println("customerId" + cId);

				String ZIP = (String) json.get("ZIPCode");

				System.out.println("ZIP Code:" + ZIP);

				customerList.add(new CustomerDetails(cId, ZIP));

			}

		}
	}

	// ********************************************************************************************************

	@SuppressWarnings({ "unchecked", "unused" })
	@RequestMapping(value = "shippingAvailability/{customerId}/{productId}", method = RequestMethod.GET)
	public ResponseEntity<ShippingAvailability> shippingAvailability(
			@PathVariable String customerId, @PathVariable String productId)
			throws Exception {

		ShippingAvailability available = new ShippingAvailability();

		int check = 0;

		if ((customerId != null) || (productId != null)) {

			try {

				for (int i = 0; i < list.size(); i++) {
					for (int j = 0; j < customerList.size(); j++) {

						if (list.get(i).getProductId().equals(productId)) {
							if (customerList.get(j).getCustomerId()
									.equals(customerId)) {
								String productZip = list.get(i)
										.getShippingLocations();

								String customerZip = customerList.get(j)
										.getZIP();

								if (productZip.contains(customerZip)) {
									for (int k = 0; k < availableList.size(); k++) {
										if ((availableList.get(k).getZIP())
												.equals(customerZip)) {
											available = availableList.get(k);
											check = 1;
											break;
										} else {

											String availabilityDetails = "templates/shippingAvailability";

											ClassLoader classLoader = new ShippingController()
													.getClass()
													.getClassLoader();

											InputStream inputStream = classLoader
													.getResourceAsStream(availabilityDetails);

											BufferedReader reader = new BufferedReader(
													new InputStreamReader(
															inputStream));
											StringBuilder out = new StringBuilder();
											String line;
											while ((line = reader.readLine()) != null) {
												out.append(line);
											}
											reader.close();

											JSONParser parser = new JSONParser();

											Object obj = parser.parse(out
													.toString());

											JSONArray jsonArray = (JSONArray) obj;

											for (int z = 0; z < jsonArray
													.size(); z++) {

												JSONObject json = (JSONObject) jsonArray
														.get(z);

												JSONObject jsonChild = (JSONObject) json
														.get("ShippingDetails");

												json.put("ShippingAvailable",
														"No");

												String shippingAvailable = (String) json
														.get("ShippingAvailable")
														.toString();

												jsonChild.put("DeliveryZip",
														"-");

												String ZIP = (String) jsonChild
														.get("DeliveryZip")
														.toString();

												jsonChild.put("City", "-");

												String city = (String) jsonChild
														.get("City").toString();

												jsonChild
														.put("EstimatedDeliveryTime",
																"-");

												String deliveryTime = (String) jsonChild
														.get("EstimatedDeliveryTime")
														.toString();

												availableList
														.add(new ShippingAvailability(
																shippingAvailable,
																ZIP, city,
																deliveryTime));

												available = availableList
														.get(k);
												ObjectWriter writer=new ObjectMapper().writer().withDefaultPrettyPrinter();
												String json1=writer.writeValueAsString(available);
												LOGGER.info("Logger information:\n"+json1);
												check = 1;
												break;
											}
										}
									}
								}

							}
						}

					}
				}

			} catch (Exception e) {
				e.printStackTrace();
			}

			if (check == 1)

				return new ResponseEntity<ShippingAvailability>(available,
						HttpStatus.OK);
			else {

				return new ResponseEntity<ShippingAvailability>(available,
						HttpStatus.NO_CONTENT);

			}
		}
		return new ResponseEntity<ShippingAvailability>(available,
				HttpStatus.BAD_REQUEST);
	}

	// ********************************************************************************************************

	public static void availableController() throws Exception {

		String availabilityDetails = "templates/shippingAvailability";

		ClassLoader classLoader = new ShippingController().getClass()
				.getClassLoader();

		InputStream inputStream = classLoader
				.getResourceAsStream(availabilityDetails);

		BufferedReader reader = new BufferedReader(new InputStreamReader(
				inputStream));
		StringBuilder out = new StringBuilder();
		String line;
		while ((line = reader.readLine()) != null) {
			out.append(line);
		}
		reader.close();

		JSONParser parser = new JSONParser();

		Object obj = parser.parse(out.toString());

		JSONArray jsonArray = (JSONArray) obj;

		@SuppressWarnings("rawtypes")
		Iterator it = jsonArray.iterator();

		if (jsonArray != null) {

			while (it.hasNext()) {

				JSONObject json = (JSONObject) it.next();

				JSONObject jsonChild = (JSONObject) json.get("ShippingDetails");

				String shippingAvailable = (String) json.get(
						"ShippingAvailable").toString();

				System.out.println("availability" + shippingAvailable);

				String ZIP = (String) jsonChild.get("DeliveryZip").toString();

				System.out.println("ZIP Code:" + ZIP);

				String city = (String) jsonChild.get("City").toString();

				System.out.println("city" + city);

				String deliveryTime = (String) jsonChild.get(
						"EstimatedDeliveryTime").toString();

				System.out.println("time" + deliveryTime);

				availableList.add(new ShippingAvailability(shippingAvailable,
						ZIP, city, deliveryTime));

			}

		}

	}

	// ********************************************************************************************************

	public static void errorController() throws Exception {

		String errorDetails = "templates/errorDetails";

		ClassLoader classLoader = new ShippingController().getClass()
				.getClassLoader();

		InputStream inputStream = classLoader.getResourceAsStream(errorDetails);

		BufferedReader reader = new BufferedReader(new InputStreamReader(
				inputStream));
		StringBuilder out = new StringBuilder();
		String line;
		while ((line = reader.readLine()) != null) {
			out.append(line);
		}
		reader.close();

		JSONParser parser = new JSONParser();

		Object obj = parser.parse(out.toString());

		JSONArray jsonArray = (JSONArray) obj;

		@SuppressWarnings("rawtypes")
		Iterator it = jsonArray.iterator();

		if (jsonArray != null) {

			while (it.hasNext()) {

				JSONObject json = (JSONObject) it.next();

				JSONObject jsonChild = (JSONObject) json.get("Error");

				int code = (Integer) jsonChild.get("Code");

				String message = (String) jsonChild.get("Message").toString();

				errorList.add(new ErrorDetails(message, code));
			}
		}

	}
}
