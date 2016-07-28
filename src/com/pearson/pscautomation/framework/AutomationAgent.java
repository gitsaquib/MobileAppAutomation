package com.pearson.pscautomation.framework;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.ios.IOSDriver;

import java.util.Optional;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.internal.TouchAction;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public abstract class AutomationAgent implements IAutomationAgent  {

	protected AppiumDriver<MobileElement> driver;

	public AutomationAgent(AppiumDriver<MobileElement> driver) {
		this.driver = driver;	
	}

	public AppiumDriver<MobileElement> getApiumDriver() {
		return driver;
	}

	public IOSDriver<MobileElement> getAndroidDriver() {
		return (IOSDriver<MobileElement>) driver;
	}

	public boolean isElementPresent(String locator) {
		try {
			driver.findElement(ByLocator(locator));
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public boolean isElementPresent(By by) {
		try {
			driver.findElement(by);
			return true;
		} catch (Exception e) {
			return false;
		}
	}	

	public String getAttribute(By by, String attribute) {
		WebElement element = find(by);
		return element.getAttribute(attribute);		
	}
	
	public void hideKeyboard() {
		driver.hideKeyboard();	
	}
	
	@Override
	public void clickOn(By by){
		driver.findElement(by).click();
	}

	@Override
	public WebElement click(By by) {
		WebElement element = find(by);
		element.click();
		return element;
	}

	@Override
	public WebElement click(ILocator locator) {
		return click(locator.toBy());
	}

	@Override
	public WebElement clear(By by) {
		WebElement element = find(by);
		element.clear();
		return element;
	}

	@Override
	public WebElement clear(ILocator locator) {
		return clear(locator.toBy());
	}

	@Override
	public WebElement setTextAndSubmit(By by, Optional<String> optional) {

		return optional != null && optional.isPresent()
		? setTextAndSubmit(by, optional.get())
				: find(by);
	}

	@Override
	public WebElement setText(By by, String text) {
		WebElement element = find(by);
		element.clear();
		element.sendKeys(text);
		return element;
	}



	@Override
	public WebElement setText(By by, Optional<String> optional) {

		return optional != null && optional.isPresent()
		? setText(by, optional.get())
				: find(by);
	}

	@Override
	public WebElement setText(ILocator locator, String text) {
		return setText(locator.toBy(), text);
	}

	@Override
	public WebElement setText(ILocator locator, Optional<String> optional) {
		return setText(locator.toBy(), optional);
	}

	@Override
	public WebElement appendText(ILocator locator, String text) {
		return appendText(locator.toBy(), text);
	}

	@Override
	public WebElement appendText(By by, String text) {
		WebElement element = find(by);
		element.sendKeys(text);
		return element;
	}

	@Override
	public WebElement setTextAndSubmit(ILocator locator, String text) {
		return setTextAndSubmit(locator.toBy(), text);
	}

	@Override
	public WebElement setTextAndSubmit(By by, String text) {
		WebElement element = find(by);
		element.clear();
		element.sendKeys(text);
		element.sendKeys(Keys.RETURN); //Note that Keys.RETURN is different that Keys.ENTER
		//Keys.ENTER wont trigger the pop up in sections where pressing enter is required
		return element;
	}

	@Override
	public WebElement setTextAndSubmit(ILocator locator, Optional<String> optional) {
		return setTextAndSubmit(locator.toBy(), optional);
	}


	@Override
	public Select selectDropDownText(By by, String textToSelect) {
		Select select = new Select(find(by));
		select.selectByVisibleText(textToSelect);
		return select;
	}

	@Override
	public Select selectDropDownText(By by, Optional<String> textToSelect) {

		return textToSelect != null && textToSelect.isPresent()
		? selectDropDownText(by, textToSelect.get())
				: new Select(find(by));
	}


	@Override
	public WebElement find(By by) {
		waitForElementPresent(by);
		return driver.findElement(by);
	}

	@Override
	public WebElement findNow(ILocator locator) {
		return findNow(locator.toBy());
	}

	@Override
	public WebElement find(ILocator locator) {
		return find(locator.toBy());
	}

	@Override
	public WebElement findNow(By by) {
		return driver.findElement(by);
	}


	public void waitForElementPresent(By by) {
		WebDriverWait wait = new WebDriverWait(driver, 10);
		wait.until(ExpectedConditions.visibilityOfElementLocated(by));
	}

	public void waitForElementNotPresent(String locator) {

		WebDriverWait wait = new WebDriverWait(driver, 10);
		wait.until(ExpectedConditions
				.invisibilityOfElementLocated(ByLocator(locator)));
	}

	// Handle locator type
	public By ByLocator(String locator) {
		By result = null;
		if (locator.startsWith("//")) {
			result = By.xpath(locator);
		} else if (locator.startsWith("css=")) {
			result = By.cssSelector(locator.replace("css=", ""));
		} else if (locator.startsWith("#")) {
			result = By.id(locator.replace("#", ""));
		} else if (locator.startsWith("name=")) {
			result = By.name(locator.replace("name=", ""));
		} else if (locator.startsWith("link=")) {
			result = By.linkText(locator.replace("link=", ""));
		} else {
			result = By.className(locator);
		}
		return result;
	}
}
