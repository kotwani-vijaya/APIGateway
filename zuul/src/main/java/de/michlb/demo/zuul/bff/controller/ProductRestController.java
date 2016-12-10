package de.michlb.demo.zuul.bff.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;
import de.michlb.demo.zuul.dto.AggregatedResult;
import de.michlb.demo.zuul.dto.Customer;
import de.michlb.demo.zuul.dto.InventoryDetails;
import de.michlb.demo.zuul.dto.Product;
import de.michlb.demo.zuul.dto.RecommendationDetails;
import de.michlb.demo.zuul.dto.RecommendationProduct;
import de.michlb.demo.zuul.dto.Recommendations;
import de.michlb.demo.zuul.feign.CustomerClient;
import de.michlb.demo.zuul.feign.InventoryClient;
import de.michlb.demo.zuul.feign.ProductClient;
import de.michlb.demo.zuul.feign.RecommendationsClient;
import de.michlb.demo.zuul.feign.ShippingClient;
import de.michlb.demo.zuul.util.JsonUtil;

@RestController
@RequestMapping("/bff")
public class ProductRestController {
	
	private static final Logger LOGGER = Logger.getLogger(ProductRestController.class.getName());
	
	private static final String AGGREGATED_DATA_URL = "http://localhost:8987/aggregated/productDetail/";
  
  
  /**
   * This call needs to be split into at least two calls and the result needs to be aggreagted
   *
   * @return Map of String of the service as key and the JSON result ass the value
   */
  @RequestMapping(value = "/productDetail/{productId}/{customerId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public AggregatedResult productDetail(@PathVariable(value = "customerId") String customerId, @PathVariable(value = "productId") String productId) {
	  final String uri = AGGREGATED_DATA_URL + customerId + "/" + productId;
	  String cid = UUID.randomUUID().toString();
	  AggregatedResult aggregatedResult = null;
	  try {
		  LOGGER.info("Request received with CID : " + cid +" for productId :" + productId + " and customerId : "+customerId);
		  RestTemplate restTemplate = new RestTemplate();
		     
		    HttpHeaders headers = new HttpHeaders();
		    headers.add("CID", cid);
		    HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);
		     
		    ResponseEntity<AggregatedResult> result = restTemplate.exchange(uri, HttpMethod.GET, entity, AggregatedResult.class);
		    aggregatedResult = result.getBody();
		    LOGGER.info("Response returned for CID : " + cid + " is : \n"+JsonUtil.toJson(aggregatedResult));
	  } catch(Exception e){
		  LOGGER.error(e);
	  }
	    return aggregatedResult;	    
  }
  
  
}
