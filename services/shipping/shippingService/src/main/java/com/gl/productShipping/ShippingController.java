package com.gl.productShipping;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
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
	
	private static final Logger LOGGER = Logger.getLogger(ShippingController.class.getName());

	ProductDetails details = null;
	
	
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
				

				list.add(new ProductDetails(pId, cities));
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
			@PathVariable String customerId, @PathVariable String productId, @RequestHeader("CID") String cid)
			throws Exception {
		LOGGER.info("Request received with CID : " + cid +" for customerId :" + customerId +" for productId :" + productId);
		ShippingAvailability available = new ShippingAvailability();

		int check = 0;

		if ((customerId != null) || (productId != null)) {
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
			ShippingAvailability shippingAvailability = JsonUtil.toObject(out.toString(), ShippingAvailability.class);

			try {
				String customerZipCode = null;
				for (CustomerDetails customer : customerList) {
					if(customer.getCustomerId().equalsIgnoreCase(customerId)) {
						customerZipCode = customer.getZIP();
						break;
					}					
				}
				if(customerZipCode != null){
					List<String> productShippingLocations = null;
					for(ProductDetails product : list){
						if(product.getProductId().equalsIgnoreCase(productId)){
							productShippingLocations = product.getShippingLocations();
							break;
						}
					}
					if(productShippingLocations != null && !productShippingLocations.isEmpty()){
						
						if(productShippingLocations.contains(customerZipCode)){
							shippingAvailability.setShippingAvailable("Yes");
							shippingAvailability.getShippingDetails().setDeliveryZip(customerZipCode);
						} else {
							shippingAvailability.setShippingAvailable("No");
							shippingAvailability.setShippingDetails(null);
						}
						
					}
				} else {
					shippingAvailability.setShippingAvailable("No");
					shippingAvailability.setShippingDetails(null);
				}
				return new ResponseEntity<ShippingAvailability>(shippingAvailability,
						HttpStatus.OK);
				

			} catch (Exception e) {
				e.printStackTrace();
			}

		}
		return new ResponseEntity<ShippingAvailability>(available,
				HttpStatus.BAD_REQUEST);
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
