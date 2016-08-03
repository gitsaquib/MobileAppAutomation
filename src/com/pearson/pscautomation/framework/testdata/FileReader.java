package com.pearson.pscautomation.framework.testdata;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class FileReader {

	/**
	 * It load the configuration file and read data
	 *
	 * @param by String properties file key
	 * @return the found value from properties file.
	 */
	public String readData(String key) throws Exception {

		if(key.equals(null) || key.equals(""))
			throw new Exception("key name is null");
		String value = "";
		try {

			Properties properties = new Properties();
			File file  = new File("TetData"+File.separator+"Config.properties");
			if (file.exists()) {
				properties.load(new FileInputStream(file));
				value = properties.getProperty(key);
			}			
		} catch (Exception e) {
			System.out.println(e);
		}
		return value;
	}


	/**
	 * It write data in configuration file
	 *
	 * @param by String key
	 * @param by String value
	 */
	public void setData(String key, String val) throws Exception {

		if(key.equals(null) || key.equals(""))
			throw new Exception("key name is null");
		try {
			File file = new File("Config.properties");			
			Properties properties = new Properties();
			properties.load(new FileInputStream(file));
			FileOutputStream obj = new FileOutputStream(file);
			properties.setProperty(key, val);
			properties.store(obj, "Update data into file ");
			//prop.save(ob, "");

		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}
}
