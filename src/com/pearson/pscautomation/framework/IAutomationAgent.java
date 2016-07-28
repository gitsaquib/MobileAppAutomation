package com.pearson.pscautomation.framework;

import java.util.Optional;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

public interface IAutomationAgent {

	public void clickOn(By by);

	/**
     * find an element by the given locator.
     * If the element is not found immediately this method waits until the element
     * becomes visible.  If the element never becomes visible before a specific timeout
     * a runtime exception is thrown.
     *
     * @param by webdriver locator
     * @return the found element.
     */
    WebElement find(By by);
    
    /**
     * find an element by the given locator.
     * If the element is not found immediately this method waits until the element
     * becomes visible.  If the element never becomes visible before a specific timeout
     * a runtime exception is thrown.
     *
     * @param locator enum based locator
     * @return the found element.
     */
    WebElement find(ILocator locator);
    
    /**
     * find an element by the given locator.
     * Does not wait for the element to become visible.
     * If the element is not visible or does not exist a runtime exception is thrown.
     *
     * @param by webdriver locator
     * @return the found element
     */
    WebElement findNow(By by);

    /**
     * find an element by the given locator.
     * Does not wait for the element to become visible.
     * If the element is not visible or does not exist a runtime exception is thrown.
     *
     * @param locator enum based locator
     * @return the found element
     */
    WebElement findNow(ILocator locator);
    
	/**
     * Sets the text of an input field.
     *
     * @param by   locator that finds an input field
     * @param text the text entered in the found input field.
     * @return the input field found by the given locator
     */
    WebElement setTextAndSubmit(By by, String text);
    
	 /**
     * Sets the text of an input field. and sends a submit event simulating pressing enter
     *
     * @param locator enum based locator that finds an input field
     * @param text    the text entered in the found input field.
     * @return the input field found by the given locator
     */
    WebElement setTextAndSubmit(ILocator locator, String text);

    /**
     * Sets the text of an input field.
     *
     * @param locator enum based locator that finds an input field
     * @param text the text entered in the found input field.
     * @return the input field found by the given locator
     */
    WebElement setText(ILocator locator, String text);

    /**
     * Appends text to an input field.
     *
     * @param locator enum based locator that finds an input field
     * @param text    the text to append to the found input field.
     * @return the input field found by the given locator
     */
    WebElement appendText(ILocator locator, String text);
    
	 /**
     * Click on the web element found by the given locator
     *
     * @param by web driver locator
     * @return the element clicked on.
     */
    WebElement click(By by);

    /**
     * Click on the web element found by the given locator
     *
     * @param locator enum based locator
     * @return the element clicked on.
     */
    WebElement click(ILocator locator);

    /**
     * Clears out the input of the web element found by the given locator
     *
     * @param by web driver based locator
     * @return the input element that was cleared.
     */
    WebElement clear(By by);

    /**
     * Clears out the input of the web element found by the given locator
     *
     * @param locator enum based locator
     * @return the input element that was cleared.
     */
    WebElement clear(ILocator locator);

   
    WebElement setText(By by, String text) ;
    
    WebElement appendText(By by, String text);
    
    WebElement setText(ILocator locator, Optional<String> optional);

    WebElement setText(By by, Optional<String> optional);

    WebElement setTextAndSubmit(ILocator locator, Optional<String> optional);

    WebElement setTextAndSubmit(By by, Optional<String> optional);

    Select selectDropDownText(By by, String textToSelect);
    Select selectDropDownText(By by, Optional<String> textToSelect);

}
