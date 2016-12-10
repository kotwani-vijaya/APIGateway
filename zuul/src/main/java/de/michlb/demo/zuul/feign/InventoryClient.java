package de.michlb.demo.zuul.feign;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient("inventory-service")
public interface InventoryClient {

  
  @RequestMapping(value = "inventory/{id}", method = RequestMethod.GET)
  String getInventory(@PathVariable(value = "id") String productId, @RequestHeader("CID") String cid);
}
