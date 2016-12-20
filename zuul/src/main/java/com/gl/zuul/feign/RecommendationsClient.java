package com.gl.zuul.feign;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient("recommendation-service")
public interface RecommendationsClient {

  @RequestMapping(value = "/recommendations/{productId}/{customerId}", method = RequestMethod.GET)
  String getRecommendations(@PathVariable(value = "productId") String productId, @PathVariable(value = "customerId") String customerId,
		  @RequestHeader("CID") String cid);
}
