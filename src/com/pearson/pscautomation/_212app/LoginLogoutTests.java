package com.pearson.pscautomation._212app;
import org.testng.annotations.Factory;
import org.testng.annotations.Test;

import com.pearson.pscautomation.framework.BaseTestCase;
import com.pearson.pscautomation.framework.FileReader;


public class LoginLogoutTests extends BaseTestCase {
	
	@Factory(dataProvider = "listDevices")
	public LoginLogoutTests(String udid, int port){
		super(udid, port);
	}
	
	@Test
	public void testLogin() throws Exception{
		LoginLogoutCommonMethods login = new LoginLogoutCommonMethods(driver);
		FileReader fileReader = new FileReader();
		String userName = fileReader.readData("UserName");
		String password = fileReader.readData("Password");
		login.loginInToApp(userName, password);
	}

}
