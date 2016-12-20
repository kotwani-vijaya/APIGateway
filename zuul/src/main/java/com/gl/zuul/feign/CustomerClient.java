package com.gl.zuul.feign;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient("customer-service")
public interface CustomerClient {

  @RequestMapping(value = "/details/{CustomerId}", method = RequestMethod.GET)
  String customerInfo(@PathVariable(value = "CustomerId") String customerId, @RequestHeader("CID") String cid);

}
