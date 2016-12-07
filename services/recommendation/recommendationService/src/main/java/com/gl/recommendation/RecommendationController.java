package com.gl.recommendation;

import java.io.File;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.LoggerContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.gl.recommendation.dto.RecommendationDetails;

@RestController
public class RecommendationController {
		
	private static LoggerContext context = (LoggerContext) LogManager.getContext(false);
	private static final Logger LOGGER = LogManager
			.getLogger(RecommendationController.class.getName());
	
	public static Map<String, List<Integer>> recommendationMap;

	@RequestMapping(value = "/recommendations/{productId}/{customerId}", method = RequestMethod.GET)
	public ResponseEntity<RecommendationDetails> getRecommendation(
			@PathVariable String productId, @PathVariable String customerId) throws RecommendationNotFoundException {
			LOGGER.info("Request logged with productId : " + productId);
		List<Integer> recommendations = recommendationMap.get(productId);
		RecommendationDetails recommendationDetails = new RecommendationDetails();
		if(recommendations == null || recommendations.isEmpty()) {
			LOGGER.info("Response logged with recommendation not found");
			return new ResponseEntity<RecommendationDetails>(recommendationDetails,
					HttpStatus.BAD_REQUEST);
		}				
		recommendationDetails.setRecommendations(recommendations);		
		return new ResponseEntity<RecommendationDetails>(recommendationDetails, HttpStatus.OK);
	}

	
	
	public void setLogProperties(String logPropPath)
	{
		File file = new File(logPropPath);
		context.setConfigLocation(file.toURI());
	}

	public static Map<String, List<Integer>> getRecommendationMap() {
		return recommendationMap;
	}

	public static void setRecommendationMap(
			Map<String, List<Integer>> recommendationMap) {
		RecommendationController.recommendationMap = recommendationMap;
	}
	
	
}
