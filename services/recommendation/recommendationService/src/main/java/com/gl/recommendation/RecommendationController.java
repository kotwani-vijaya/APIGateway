package com.gl.recommendation;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.gl.recommendation.dto.RecommendationDetails;
import com.gl.recommendation.util.JsonUtil;

@RestController
public class RecommendationController {
		
	private static final Logger LOGGER = Logger.getLogger(RecommendationController.class.getName());
	
	public static Map<String, List<Integer>> recommendationMap;

	@RequestMapping(value = "/recommendations/{productId}/{customerId}", method = RequestMethod.GET)
	public ResponseEntity<RecommendationDetails> getRecommendation(
			@PathVariable String productId, @PathVariable String customerId, @RequestHeader("CID") String cid) throws RecommendationNotFoundException,
		JsonGenerationException, JsonMappingException, IOException {
		LOGGER.info("Request received with CID : " + cid +" for productId :" + productId);
		List<Integer> recommendations = recommendationMap.get(productId);
		RecommendationDetails recommendationDetails = new RecommendationDetails();
		if(recommendations == null || recommendations.isEmpty()) {
			LOGGER.info("Response logged with recommendation not found");
			return new ResponseEntity<RecommendationDetails>(recommendationDetails,
					HttpStatus.BAD_REQUEST);
		}				
		recommendationDetails.setRecommendations(recommendations);	
		LOGGER.info("Response returned for CID : " + cid +" and productId :" + productId + " is : "+JsonUtil.toJson(recommendationDetails));
		return new ResponseEntity<RecommendationDetails>(recommendationDetails, HttpStatus.OK);
	}


	public static Map<String, List<Integer>> getRecommendationMap() {
		return recommendationMap;
	}

	public static void setRecommendationMap(
			Map<String, List<Integer>> recommendationMap) {
		RecommendationController.recommendationMap = recommendationMap;
	}
	
	
}
