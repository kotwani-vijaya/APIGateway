package com.gl.customer;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class GetCustomerProperties 
{
	private static InputStream inputStream;
	String path = null;
	public void getProperties(String propertiesLocation) throws IOException {
		inputStream = new FileInputStream(propertiesLocation);
		Properties prop = new Properties();
		
		String propFileName = "ProductInformation.properties";
		try {
			if (inputStream != null) {
				prop.load(inputStream);
			} else {
				throw new FileNotFoundException("property file '"
						+ propFileName + "' not found in the classpath");
			}

			path = prop.getProperty("FilePath");
		} catch (Exception e) {
			System.out.println("Exception: " + e);
		}
	}
	
	public String location()
	{
		return path;
	}
	
	public File[] getFiles()
	{
		File folder = new File(path);
		File[] listOfFiles = folder.listFiles();
		return listOfFiles;
	}
}
