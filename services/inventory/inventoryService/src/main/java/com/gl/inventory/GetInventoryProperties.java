package com.gl.inventory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class GetInventoryProperties 
{
	private static InputStream inputStream;
	
	String inventoryPath = null;
	String inventoryDetailPath = null;
	public void getProperties(String propertiesLocation) throws IOException {
		inputStream = new FileInputStream(propertiesLocation);
		Properties prop = new Properties();
		
		String propFileName = "InventoryService.properties";
		try {
			if (inputStream != null) {
				prop.load(inputStream);
			} else {
				throw new FileNotFoundException("property file '"
						+ propFileName + "' not found in the classpath");
			}

			inventoryPath = prop.getProperty("InventoryPath");
			inventoryDetailPath = prop.getProperty("InventoryDetailPath");
		} catch (Exception e) {
			System.out.println("Exception: " + e);
		}
	}
	
	public String inventoryLocation()
	{
		return inventoryPath;
	}
	
	public String inventoryDetailLocation()
	{
		return inventoryDetailPath;
	}
	
	public File[] getInventoryDeatilFiles()
	{
		File folder = new File(inventoryDetailPath);
		File[] listOfFiles = folder.listFiles();
		return listOfFiles;
	}
	
}
