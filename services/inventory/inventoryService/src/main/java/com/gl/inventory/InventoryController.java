package com.gl.inventory;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import javax.websocket.server.PathParam;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.LoggerContext;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

@RestController
public class InventoryController {
	private static File[] listOfFiles;
	private static String location;
	private static LoggerContext context = (LoggerContext) LogManager
			.getContext(false);
	private static final Logger LOGGER = LogManager
			.getLogger(InventoryController.class.getName());
	private static Inventory inventory = new Inventory();

	@RequestMapping(value = "GetInventory", method = RequestMethod.GET)
	public ResponseEntity<InventoryDetails> getInventory(
			@PathParam("ProductId") String id,@RequestHeader("CID") String cid)throws ProductNotFoundException {
		LOGGER.info("Request received with CID : " + cid);
		LOGGER.info("Request logged with Request Id as : " + id);
		String product = "Product-" + id;
		InventoryDetails inventoryDetails = new InventoryDetails();
		if (id.equals("") || id.equals("\t") || id.equals("\n")) {
			LOGGER.info("Response returned with CID : " + cid+" .The request was empty.");
			LOGGER.info("Response logged with empty Request Id");
			return new ResponseEntity<InventoryDetails>(inventoryDetails,
					HttpStatus.BAD_REQUEST);
		}

		int flag = 0;
		if (inventory.getProductId().contains(id)) {
			try {
				for (int i = 0; i < listOfFiles.length; i++) {

					if (listOfFiles[i].isFile()) {

						if (listOfFiles[i].getName().equals(product + ".txt")) {
							InventoryDetails p = readJsonFile(location
									+ product + ".txt");
							ObjectWriter ow = new ObjectMapper().writer()
									.withDefaultPrettyPrinter();
							String json = ow.writeValueAsString(p);
							LOGGER.info("Response returned with CID : " + cid+" .The response was successful.");
							LOGGER.info("Response logged with product as : \n"
									+ json);
							flag = 1;
							return new ResponseEntity<InventoryDetails>(p,
									HttpStatus.OK);
						}
					}

				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		if (flag == 0) {
			LOGGER.info("Response returned with CID : " + cid+" .The response was empty because the Product was not found.");
			LOGGER.info("Response logged with product not found");
			throw new ProductNotFoundException("1244", "Invalid Product Id");
		}
		return new ResponseEntity<InventoryDetails>(inventoryDetails,HttpStatus.BAD_REQUEST);
	}

	public static InventoryDetails readJsonFile(String path) throws IOException {
		JSONParser parser = new JSONParser();
		InventoryDetails invDet = null;
		try {
			Object obj = parser.parse(new FileReader(path));
			JSONObject jsonObject = (JSONObject) obj;
			double apiVersion = (Double) jsonObject.get("ApiVersion");
			String inStock = (String) jsonObject.get("InStock");
			long quantityAvailable = (Long) jsonObject
					.get("QuantityAvailable");
			invDet = new InventoryDetails(apiVersion, inStock,
					quantityAvailable);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return invDet;
	}

	public void setFiles(File[] productFiles) {
		listOfFiles = productFiles;
	}

	public void setFileLocation(String loc) {
		location = loc;
	}

	public void setLogProperties(String logPropPath) {
		File file = new File(logPropPath);
		context.setConfigLocation(file.toURI());
	}

	@SuppressWarnings("unchecked")
	public void settingInventory(String inventoryLocation) {
		JSONParser parser = new JSONParser();
		try {
			Object obj = parser.parse(new FileReader(inventoryLocation));
			JSONObject jsonObject = (JSONObject) obj;
			JSONArray productId = (JSONArray) jsonObject.get("productId");
			inventory.setProductId(productId);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
