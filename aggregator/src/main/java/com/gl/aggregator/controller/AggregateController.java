package com.gl.aggregator.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.gl.aggregator.dto.AggregatedResult;
import com.gl.aggregator.dto.Customer;
import com.gl.aggregator.dto.InventoryDetails;
import com.gl.aggregator.dto.Product;
import com.gl.aggregator.dto.RecommendationDetails;
import com.gl.aggregator.dto.RecommendationProduct;
import com.gl.aggregator.dto.Recommendations;
import com.gl.aggregator.dto.ShippingAvailability;
import com.gl.aggregator.feign.CustomerClient;
import com.gl.aggregator.feign.InventoryClient;
import com.gl.aggregator.feign.ProductClient;
import com.gl.aggregator.feign.RecommendationsClient;
import com.gl.aggregator.feign.ShippingClient;
import com.gl.aggregator.util.JsonUtil;

@RestController
@RequestMapping("/aggregated")
public class AggregateController {
	
	private static final Logger LOGGER = Logger.getLogger(AggregateController.class.getName());

  @Autowired
  private ProductClient productClient;

  @Autowired
  private CustomerClient customerClient;
  
  @Autowired
  private ShippingClient shippingClient;
  
  @Autowired
  private RecommendationsClient recommendationsClient;
  
  @Autowired
  private InventoryClient inventoryClient;

  
  /**
   * This call needs to be split into at least two calls and the result needs to be aggreagted
   *
   * @return Map of String of the service as key and the JSON result ass the value
   */
  @RequestMapping(value = "/productDetail/{productId}/{customerId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public AggregatedResult productDetail(@PathVariable(value = "customerId") String customerId, @PathVariable(value = "productId") String productId) {
	  AggregatedResult aggregatedResult = new AggregatedResult();
	  try {
		  String cid = UUID.randomUUID().toString();
		  LOGGER.info("Request received with CID : " + cid +" for productId :" + productId + " and customerId : "+customerId);
		  HttpHeaders responseHeaders = new HttpHeaders();
			responseHeaders.add("Access-Control-Allow-Origin", "localhost");
			responseHeaders.add("Access-Control-Allow-Methods", "GET, POST, PATCH, PUT, DELETE, OPTIONS");
			responseHeaders.add("Access-Control-Allow-Header", "Content-Type");		
		Product product = JsonUtil.toObject(productClient.getProduct(productId, cid), Product.class);
		if(product != null) {
			InventoryDetails inventory = JsonUtil.toObject(inventoryClient.getInventory(productId, cid), InventoryDetails.class);
			if(inventory != null) {
				product.setQty(String.valueOf(inventory.getQuantityAvailable()));
			}
			RecommendationDetails recommendationDetails = JsonUtil.toObject(recommendationsClient.getRecommendations(productId, customerId, cid), RecommendationDetails.class);
			if(recommendationDetails != null) {
				List<RecommendationProduct> recommendedProdList = new ArrayList<RecommendationProduct>();
				for(Integer prodId : recommendationDetails.getRecommendations()) {
					Product prod = JsonUtil.toObject(productClient.getProduct(prodId.toString(), cid), Product.class);
					RecommendationProduct recommendedProd = new RecommendationProduct();
					recommendedProd.setName(prod.getName());
					recommendedProd.setDescription(prod.getDescription());
					recommendedProd.setCost(prod.getCost());
					recommendedProd.setImageUrl(prod.getImageUrl());
					recommendedProdList.add(recommendedProd);
				}
				Recommendations recommendations = new Recommendations();
				recommendations.setRecommendations(recommendedProdList);
				aggregatedResult.setRecommendations(recommendations);
			}
			aggregatedResult.setProduct(product);
		}
		Customer customer = JsonUtil.toObject(customerClient.customerInfo(customerId, cid), Customer.class);
		aggregatedResult.setCustomer(customer);
		ShippingAvailability shippingAvailability = JsonUtil.toObject(shippingClient.info(customerId, productId, cid), ShippingAvailability.class);
		aggregatedResult.setShippingAvailability(shippingAvailability);
		LOGGER.info("Response returned for CID : " + cid + " is : \n"+JsonUtil.toJson(aggregatedResult));
	} catch (Exception e) {
		LOGGER.error(e);
	}

    return aggregatedResult;
  }
  
  
}
