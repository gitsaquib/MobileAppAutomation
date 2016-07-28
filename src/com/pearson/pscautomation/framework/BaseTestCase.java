package com.pearson.pscautomation.framework;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.ios.IOSDriver;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FilenameUtils;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;

public class BaseTestCase {

	protected AppiumDriver<MobileElement> driver;
	protected int timeOut = 30;	
	private int port = 0;
	private String udid;

	public BaseTestCase(String udid, int port) {
		this.udid = udid;
		this.port = port;
	}

	@BeforeClass
	public void setUp() {		
		try {
			driver = new IOSDriver<MobileElement>(new URL("http://127.0.0.1:"+port+"/wd/hub"), getDesiredCapabilities(this.getAppAbsoultePath()));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		driver.manage().timeouts().implicitlyWait(timeOut, TimeUnit.SECONDS);	

	}

	@AfterClass
	public void tearDown() throws Exception {
		driver.quit();
	}

	/**
	 * @author Javed
	 * Get absolute path of android application apk file.
	 * Apk file is under the app folder
	 * @param no parameter
	 * @return absolute path.
	 */
	public String getAppAbsoultePath(){
		File classpathRoot = new File(System.getProperty("user.dir"));
		File appDir = new File(classpathRoot, "/app");
		File app = new File(appDir, getAppName());
		String appName = app.getAbsolutePath();
		return appName;
	}

	/**
	 * @author Javed
	 * Setup configuration in DesiredCapabilities which appium used to run test 
	 * @param appPath application absolute path
	 * @return object of DesiredCapabilities.
	 */
	public DesiredCapabilities getDesiredCapabilities(String appPath) throws Exception{
		if(this.udid.equals(null) ||  this.udid.equals("") )
			throw new Exception("udid value is null");

		DesiredCapabilities capabilities = new DesiredCapabilities();
		capabilities.setCapability("platformName", "Android");
		capabilities.setCapability("deviceName", "Android");
		capabilities.setCapability("platformVersion", "4.4.2");
		capabilities.setCapability("app", appPath);
		/*capabilities.setCapability("appPackage", "");
		capabilities.setCapability("appActivity", "");*/
		capabilities.setCapability("udid", udid);
		return capabilities;
	}


	/**
	 * @author Javed
	 * It relaunch the application.
	 */
	public void relaunchApp() {
		driver.closeApp();
		driver.launchApp();
	}

	/**
	 * @author Javed
	 * This function get run time application name from  app folder
	 */
	public String getAppName() {
		String fileName = "";
		File folder = new File("app");
		File[] listOfFiles = folder.listFiles();
		for (File listFile : listOfFiles) {
			String fileExtension = FilenameUtils.getExtension(listFile
					.getAbsolutePath());
			if (fileExtension.equals("apk")) {
				fileName = listFile.getName();
				break;
			}
		}
		return fileName;

	}


	/**
	 * @author Javed
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
	 * @author Javed
	 * prepare data provider contains devices id and Appium server port 
	 * By default Appium server port stared from 4723 and one increment for next device 
	 */
	@DataProvider(name = "listDevices", parallel = true)
	public static Object[][] listDevices() {

		Object obj[][] = new Object[getAttachedDevicesList().size()][2];
		List<String> devicesList = getAttachedDevicesList();
		for (int i = 0; i < devicesList.size(); i++) {
			obj[i][0] = devicesList.get(i);
			obj[i][1] = 4723 + i;
		}
		return obj;
	}

	/**
	 * @author Javed
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
