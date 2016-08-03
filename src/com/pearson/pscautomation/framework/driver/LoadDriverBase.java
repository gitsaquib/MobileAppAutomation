package com.pearson.pscautomation.framework.driver;

import java.io.File;
import java.util.Map;

import org.openqa.selenium.remote.DesiredCapabilities;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;

public abstract class LoadDriverBase {
	public abstract AppiumDriver<MobileElement> loadDriverMethod(Map<String, String> properties);
	public abstract String getAppName();
	
	protected DesiredCapabilities getDesiredCapabilities(String appPath, Map<String, String> properties) throws Exception{
		DesiredCapabilities capabilities = new DesiredCapabilities();
		capabilities.setCapability("platformName", properties.get("Platform"));
		capabilities.setCapability("deviceName", properties.get("DeviceName"));
		capabilities.setCapability("platformVersion", properties.get("Version"));
		capabilities.setCapability("app", appPath);
		capabilities.setCapability("udid", properties.get("UDID"));
		return capabilities;
	}

	protected String getAppAbsoultePath(){
		File classpathRoot = new File(System.getProperty("user.dir"));
		File appDir = new File(classpathRoot, "/app");
		File app = new File(appDir, getAppName());
		String appName = app.getAbsolutePath();
		return appName;
	}
	
}
