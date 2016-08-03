package com.pearson.pscautomation.framework;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;

import com.pearson.pscautomation.framework.config.PropertyReader;
import com.pearson.pscautomation.framework.driver.LoadiOSDriver;

public class BaseTestCase {
	
	static Logger log;
	protected AppiumDriver<MobileElement> driver;
	protected static Map<String, String> propMap;
	protected int timeOut = 30;
	protected int port = 0;
	protected String udid;

	public BaseTestCase(String udid, int port) {
		this.udid = udid;
		this.port = port;
	}
	
	public static void init(){
		log = Logger.getLogger(BaseTestCase.class.getName());
		propMap = PropertyReader.readConfigFile();
	}

	@BeforeClass
	public void loadDriverMethod() {		
		try {
			LoadiOSDriver loadiOSDriver = new LoadiOSDriver();
			if(propMap.get("Platform").equalsIgnoreCase("iOS")) {
				driver = loadiOSDriver.loadDriverMethod(propMap);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		driver.manage().timeouts().implicitlyWait(timeOut, TimeUnit.SECONDS);	

	}

	@AfterClass
	public void tearDown() throws Exception {
		driver.quit();
	}

	/**
	 * It re-launches the application.
	 */
	public void relaunchApp() {
		driver.closeApp();
		driver.launchApp();
	}

	/**
	 * It capture all devices id attached with machine
	 * @return return list of attached devices id .
	 */
	public static List<String> getAttachedDevicesList() {
		List<String> devicesID = new ArrayList<String>();
		try {
			Process process = Runtime.getRuntime().exec(getAndroidPath() + "//platform-tools//adb devices");
			BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
			String s;
			while ((s = reader.readLine()) != null) {

				if (s.contains("device") && !s.contains("attached")) {
					String[] device = s.split("\t");
					devicesID.add(device[0]);
				}
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
		return devicesID;
	}


	/**
	 * prepare data provider contains devices id and Appium server port 
	 * By default Appium server port stared from 4723 and one increment for next device 
	 */
	@DataProvider(name = "listDevices", parallel = true)
	public static Object[][] listDevices() {
		List<String> listOfDevices = getAttachedDevicesList();
		Object obj[][] = new Object[listOfDevices.size()][2];
		List<String> devicesList = listOfDevices;
		for (int i = 0; i < devicesList.size(); i++) {
			obj[i][0] = devicesList.get(i);
			obj[i][1] = 4723 + i;
		}
		return obj;
	}
	
	@DataProvider(name = "listDevicesFromConfig", parallel = true)
	public static Object[][] listDevicesFromConfig() {
		init();
		Object[][] objectArr = new Object[1][2];
		objectArr[0][0] = propMap.get("UDID").toString();
		objectArr[0][1] = Integer.valueOf(propMap.get("USBPort"));
		return  objectArr;
	}

	/**
	 * @return android home path
	 */
	public static String getAndroidPath() {
		String androidHome = null;
		Map<String, String> env = System.getenv();
		for (String envName : env.keySet()) {
			if (envName.equals("ANDROID_HOME"))
				androidHome = env.get(envName);
		}
		if (androidHome.equals(null))
			throw new NullPointerException(
					"Android Home path not set in machine");
		return androidHome;
	}

	public void appReset() {
		driver.resetApp();
	}


	// Get random integer
	public int getRandomInteger(int aStart, int aEnd) {
		Random aRandom = new Random();
		if (aStart > aEnd) {
			throw new IllegalArgumentException("Start cannot exceed End.");
		}
		// get the range, casting to long to avoid overflow problems
		long range = (long) aEnd - (long) aStart + 1;
		// compute a fraction of the range, 0 <= frac < range
		long fraction = (long) (range * aRandom.nextDouble());
		int randomNumber = (int) (fraction + aStart);
		return randomNumber;
	}

}
