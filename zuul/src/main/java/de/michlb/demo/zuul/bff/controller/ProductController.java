package de.michlb.demo.zuul.bff.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.apache.commons.lang.math.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import de.michlb.demo.zuul.dto.AggregatedResult;
import de.michlb.demo.zuul.dto.Customer;
import de.michlb.demo.zuul.dto.Product;
import de.michlb.demo.zuul.dto.RecommendationDetails;
import de.michlb.demo.zuul.dto.RecommendationProduct;
import de.michlb.demo.zuul.dto.Recommendations;
import de.michlb.demo.zuul.feign.CustomerClient;
import de.michlb.demo.zuul.feign.ProductClient;
import de.michlb.demo.zuul.feign.RecommendationsClient;
import de.michlb.demo.zuul.feign.ShippingClient;
import de.michlb.demo.zuul.util.JsonUtil;

@RestController
@RequestMapping("/bff/aggregated")
public class ProductController {

  @Autowired
  private ProductClient productClient;

  @Autowired
  private CustomerClient customerClient;
  
  @Autowired
  private ShippingClient shippingClient;
  
  @Autowired
  private RecommendationsClient recommendationsClient;

  
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
		Product product = JsonUtil.toObject(productClient.getProduct(productId, cid), Product.class);
		if(product != null) {
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
	} catch (Exception e) {
		e.printStackTrace();
	}

    return aggregatedResult;
  }
  
  
}
