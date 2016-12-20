package com.gl.services.recommendation.util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FileUtil {
	
	public static Map<String, List<Integer>> readRecommendations(String filePath) {
		Map<String, List<Integer>> recommendationsMap = new HashMap<String, List<Integer>>();
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader(filePath));
			StringBuilder out = new StringBuilder();
			String line;
			while ((line = reader.readLine()) != null) {
				out.append(line);
			}
			recommendationsMap = JsonUtil.toObject(out.toString(), Map.class);
		} catch(Exception e){
			e.printStackTrace();
		} finally {
			if(reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return recommendationsMap;
	}

}
