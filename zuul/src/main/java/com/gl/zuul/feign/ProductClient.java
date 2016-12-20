package com.gl.zuul.feign;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient("product-service")
public interface ProductClient {

  @RequestMapping(value = "api/product/detail", method = RequestMethod.GET)
  String detail();
  

  @RequestMapping(value = "products/{ProductId}", method = RequestMethod.GET)
  String getProduct(@PathVariable(value = "ProductId") String productId, @RequestHeader("CID") String cid);
}
