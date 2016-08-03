package com.pearson.pscautomation.framework.driver;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.ios.IOSDriver;

import java.io.File;
import java.net.URL;
import java.util.Map;

import org.apache.commons.io.FilenameUtils;

public class LoadiOSDriver extends LoadDriverBase {

	@Override
	public AppiumDriver<MobileElement> loadDriverMethod(Map<String, String> properties) {
		try {
			return new IOSDriver<MobileElement>(new URL("http://"+properties.get("Host")+":"+properties.get("Port")+"/wd/hub"), getDesiredCapabilities(getAppAbsoultePath(), properties));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public String getAppName() {
		String fileName = "";
		File folder = new File("app");
		File[] listOfFiles = folder.listFiles();
		for (File listFile : listOfFiles) {
			String fileExtension = FilenameUtils.getExtension(listFile
					.getAbsolutePath());
			if (fileExtension.equals("ipa")) {
				fileName = listFile.getName();
				break;
			}
		}
		return fileName;
	}
}
