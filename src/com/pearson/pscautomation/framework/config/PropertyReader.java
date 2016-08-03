package com.pearson.pscautomation.framework.config;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;

public class PropertyReader {

	public static Map<String, String> readConfigFile() {
		Map<String, String> propMap = new HashMap<String, String>();
		try
		{
			File inputFile = new File("Resources"+File.separator+"App.config");
	         SAXReader reader = new SAXReader();
	         Document document = reader.read( inputFile );
	         List<Node> nodes = document.getRootElement().elements();
	         for (Node node : nodes) {
	        	 propMap.put(node.getName(), node.getText());
	         }
		} catch(Exception e) {
			e.printStackTrace();
		}
		return propMap;
	}
}
