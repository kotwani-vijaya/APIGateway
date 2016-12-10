package de.michlb.demo.zuul.feign;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient("shipping-service")
public interface ShippingClient {

  @RequestMapping(value = "shippingAvailability/{customerId}/{productId}", method = RequestMethod.GET)
  String info(@PathVariable(value = "customerId") String customerId, @PathVariable(value = "productId") String productId,
		  @RequestHeader("CID") String cid);
}
