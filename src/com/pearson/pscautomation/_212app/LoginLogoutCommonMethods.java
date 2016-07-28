package com.pearson.pscautomation._212app;

import static com.pearson.pscautomation._212app.locator.LoginLogoutLocator.LoginButton;
import static com.pearson.pscautomation._212app.locator.LoginLogoutLocator.Password;
import static com.pearson.pscautomation._212app.locator.LoginLogoutLocator.UserName;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;

import com.pearson.pscautomation.framework.AutomationAgent;

public class LoginLogoutCommonMethods extends AutomationAgent {

	public LoginLogoutCommonMethods(AppiumDriver<MobileElement> driver) {
		super(driver);
	}

	public LoginLogoutCommonMethods clickOnLogin(){
		click(LoginButton.toBy());
		return this;
	}
	
	public LoginLogoutCommonMethods loginInToApp(String userName, String pass){
		setText(UserName.toBy(), userName);
		hideKeyboard();
		setText(Password.toBy(), pass);
		hideKeyboard();
		click(LoginButton.toBy());
		return new LoginLogoutCommonMethods(driver);
	}
}
