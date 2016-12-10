package com.gl.aggregator.util;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 
 * @author vineeta.singh
 *
 */
public class JsonUtil {

	private static final ObjectMapper ObjectMapper = new ObjectMapper();

	public static String toJson(Object obj) throws JsonGenerationException, JsonMappingException, IOException {
		return (obj != null) ? ObjectMapper.writeValueAsString(obj) : null;
	}

	public static <T> T toObject(String content, Class<T> clazz) throws JsonGenerationException, JsonMappingException, IOException {
		return (content != null) ? ObjectMapper.readValue(content, clazz) : null;
	}

}
