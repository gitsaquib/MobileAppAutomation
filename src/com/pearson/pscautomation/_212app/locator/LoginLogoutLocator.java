package com.pearson.pscautomation._212app.locator;

import org.openqa.selenium.By;

import com.pearson.pscautomation.framework.ILocator;

public enum LoginLogoutLocator implements ILocator{
	   
    LoginButton(By.id("com.pearson:id/btn_login")),
    Password(By.id("com.pearson:id/edt_password")),
    UserName(By.id("com.pearson:id/edt_display_name"));
    
    private final By locator;

    LoginLogoutLocator(By locator) {
        this.locator = locator;
    }
    
    @Override
    public By toBy() {
        return locator;
    }
    
    public static By getChannelLocator(int index) {
        return By.xpath(String.format(
              "//*[@id='com.pearson:id/imageView']['%s']",
              index));
  }

       
}
