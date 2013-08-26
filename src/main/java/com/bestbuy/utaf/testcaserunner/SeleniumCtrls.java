package com.bestbuy.utaf.testcaserunner;

import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.codehaus.plexus.util.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.openqa.selenium.support.ui.Select;

import com.bestbuy.utaf.enumutil.SelectBrowserEnum;
import com.bestbuy.utaf.util.AjaxWait;

public class SeleniumCtrls { 
	private static Logger log = Logger.getLogger("SeleniumCtrls");
	private WebDriver driver;
	@SuppressWarnings("unused")
	private int waitTime;


	/**
	 * Constructor
	 * @param browser
	 * @param url
	 * @param waitTime
	 */
	public SeleniumCtrls(SelectBrowserEnum browser, String url, String resolution, int waitTime) {
		this.waitTime = waitTime;
		this.driver = browser.execute(resolution);
		log.info("URL in SeleniumCtrls : "+url);
		driver.get(url);
		AjaxWait ajaWait = new AjaxWait();
		ajaWait.waitForAjax(driver, waitTime);
	}

	/**
	 * Close Browser
	 */
	public void closeBrowser() {
		driver.quit();
	}

	/**
	 * Destroy Browser
	 */
	public void destroy() {
		driver.close();
	}

	/**
	 * Get Current URL
	 * @return String
	 */
	public String getCurrentUrl() {
		return driver.getCurrentUrl();
	}

	/**
	 * 
	 * @param driver
	 * @param xpathValue
	 * @param waitTime
	 * @return boolean
	 */
	public static boolean clickElement(WebDriver driver, String xpathValue,
			int waitTime) {
		boolean found = true;
		try {
			WebElement element = driver.findElement(By.xpath(xpathValue));
			element.click();
		} catch (Exception e) {
			found = false;
			log.info("Throwable Exception in clickElement \n" + e.getMessage());
		}
		AjaxWait ajaWait = new AjaxWait();
		ajaWait.waitForAjax(driver, waitTime);
		return found;
	}

	/**
	 * 
	 * @param driver
	 * @param xpathValue
	 * @return String
	 */
	public static String getElementText(WebDriver driver, String xpathValue) {
		String btnTxt = "Fail";
		try {
			WebElement element = driver.findElement(By.xpath(xpathValue));
			btnTxt = element.getText();
			if(StringUtils.isEmpty(btnTxt)){
				btnTxt = element.getAttribute("value");
			}
		} catch (Exception e)  {
			log.info("Throwable Exception in getElementText \n" + e.getMessage());
		}
		return btnTxt;
	}

	/**
	 * 
	 * @param driver
	 * @param xpathValue
	 * @return Dimension
	 */
	public Dimension getElementDimensions(WebDriver driver, String xpathValue) {
		Dimension dimension = null;
		try {
			WebElement element = driver.findElement(By.xpath(xpathValue));
			dimension = element.getSize();
		} catch (Exception e) {
			log.info("Throwable Exception in getElementDimensions \n" + e.getMessage());
		}
		return dimension;
	}

	/**
	 * 
	 * @param driver
	 * @param xpathValue
	 * @return boolean
	 */
	public static boolean isElementVisible(WebDriver driver, String xpathValue) {
		boolean status = false;
		try {
			WebElement element = driver.findElement(By.xpath(xpathValue));
			status = element.isDisplayed();
		} catch (Exception e) {
			log.info("Throwable Exception in isElementVisible \n" + e.getMessage());
		}
		return status;
	}

	/**
	 * 
	 * @param xpath
	 * @param selection
	 */
	public void clickDropDownOption(String xpath, String selection) {
		EventFiringWebDriver eventDriver = null;
		eventDriver = new EventFiringWebDriver(getDriver());
		eventDriver.get(getDriver().getCurrentUrl());
		eventDriver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

		eventDriver.findElement(By.xpath(xpath)).click();
		Select sel = new Select(getDriver().findElement(By.tagName(selection)));
		sel.selectByValue("fil");
		eventDriver.close();
	}

	/**
	 * Get Driver
	 * @return WebDriver
	 */
	public WebDriver getDriver() {
		return driver;

	}

	/**
	 * Set Driver
	 * @param driver
	 */
	public void setDriver(WebDriver driver) {
		this.driver = driver;
	}

	/**
	 * 
	 * @param driver
	 * @param xpathValue
	 * @param value
	 * @param waitTime
	 * @return boolean
	 */
	public static boolean setTextBox(WebDriver driver, String xpathValue,
			String value, int waitTime)  {
		boolean found = false;
		try {
			WebElement element = driver.findElement(By.xpath(xpathValue));
			element.sendKeys(value);
			found = true;
		} catch (Exception e) {
			found = false;
			log.info("Throwable Exception in setTextBox \n" + e.getMessage());
		}
		AjaxWait ajaxWait = new AjaxWait();
		ajaxWait.waitForAjax(driver, waitTime);
		return found;
	}

	/**
	 * 
	 * @param driver
	 * @param xpathValue
	 * @return String
	 */
	public static String getTextboxValue(WebDriver driver, String xpathValue) {
		String value = "";
		try {
			WebElement element = driver.findElement(By.xpath(xpathValue));
			value = element.getAttribute("value");
		} catch (Exception e) {
			value = "Fail";
			log.info("Throwable Exception in getTextboxValue \n" + e.getMessage());
		}
		return value;
	}

	/**
	 * 
	 * @param driver
	 * @param xpathValue
	 * @return boolean
	 */
	public boolean isTextboxVisible(WebDriver driver, String xpathValue) {
		return driver.findElement(By.xpath(xpathValue)).isDisplayed();
	}

	/**
	 * 
	 * @param driver
	 * @param xpathValue
	 * @param waitTime
	 * @return boolean
	 */
	public static boolean clickTextboxButton(WebDriver driver,
			String xpathValue, int waitTime) {
		boolean msg = false;
		try {
			WebElement element = driver.findElement(By.xpath(xpathValue));
			element.click();
			msg = true;
			AjaxWait ajaxWait = new AjaxWait();
			ajaxWait.waitForAjax(driver, waitTime);
		} catch (Exception e) {
			msg = false;
			log.info("Throwable Exception in clickTextboxButton \n" + e.getMessage());
		}
		return msg;
	}

	/**
	 * 
	 * @param driver
	 * @param xpathValue
	 * @param textValue
	 * @return boolean
	 */
	public static boolean selectText(WebDriver driver, String xpathValue,String textValue) {
		boolean result = false;
		try {
			WebElement element = driver.findElement(By.xpath(xpathValue));
			Select selObj = new Select(element);
			selObj.selectByVisibleText(textValue);
			result = true;
		} catch (Exception e) {
			log.info("Throwable Exception in selectText \n" + e.getMessage());
		}
		return result;
	}


	/**
	 * 
	 * @param driver
	 * @param xpathValue
	 * @param waitTime
	 * @return
	 */
	public static boolean clickCheckbox(WebDriver driver,String xpathValue, int waitTime) {
		boolean msg = false;
		try {
			WebElement element = driver.findElement(By.xpath(xpathValue));
			if(!element.isSelected())
			{
				element.click();
			}
			msg = true;
		} catch (Exception e) {
			msg = false;
			log.info("Throwable Exception in clickCheckbox \n" + e.getMessage());
		}
		return msg;
	}

	/**
	 * 
	 * @param driver
	 * @param xpathValue
	 * @param waitTime
	 * @return
	 */
	public static boolean clickRadioButton(WebDriver driver,String xpathValue, int waitTime) {
		boolean msg = false;
		try {
			WebElement element = driver.findElement(By.xpath(xpathValue));
			if(!element.isSelected())
			{
				element.click();
			}
			msg = true;
		} catch (Exception e) {
			msg = false;
			log.info("Throwable Exception in clickRadioButton \n" + e.getMessage());
		}
		return msg;
	}

	/**
	 * 
	 * @param driver
	 * @param xpathValue
	 * @param textValue
	 * @return
	 */
	public static boolean clickDropdown(WebDriver driver, String xpathValue,String textValue) {
		boolean result = false;
		try {
			WebElement element = driver.findElement(By.xpath(xpathValue));
			element.click();
			element.sendKeys(textValue);
			result = true;
		} catch (Exception e) {
			log.info("Throwable Exception in clickDropdown \n" + e.getMessage());
		}
		return result;
	}

	/**
	 * 
	 * @param driver
	 * @param xpathValue
	 * @param value
	 * @param waitTime
	 * @return
	 */
	public static boolean setTextareaValue(WebDriver driver, String xpathValue,String value, int waitTime)  {
		boolean found = false;
		try {
			if(isTextareaVisible(driver, xpathValue)){
				WebElement element = driver.findElement(By.xpath(xpathValue));
				element.sendKeys(value);
				found = true;
			}
		} catch (Exception e) {
			found = false;
			log.info("Throwable Exception in setTextareaValue \n" + e.getMessage());
		}
		AjaxWait ajaxWait = new AjaxWait();
		ajaxWait.waitForAjax(driver, waitTime);
		return found;
	}

	/**
	 * 
	 * @param driver
	 * @param xpathValue
	 * @return String
	 */
	public static String getTextareaValue(WebDriver driver, String xpathValue) {
		String value = "";
		try {
			if(isTextareaVisible(driver, xpathValue)){
				WebElement element = driver.findElement(By.xpath(xpathValue));
				value = element.getAttribute("value");
			}
		} catch (Exception e) {
			value = "Fail";
			log.info("Throwable Exception in getTextareaValue \n" + e.getMessage());
		}
		return value;
	}

	/**
	 * 
	 * @param driver
	 * @param xpathValue
	 * @return
	 */
	public static boolean isTextareaVisible(WebDriver driver, String xpathValue) {
		return driver.findElement(By.xpath(xpathValue)).isDisplayed();
	}

	/**
	 * 
	 * @param driver
	 * @param xpathValue
	 * @param waitTime
	 * @return
	 */
	public static boolean clickTextareaBox(WebDriver driver,String xpathValue, int waitTime) {
		boolean msg = false;
		try {
			if(isTextareaVisible(driver, xpathValue)){
				WebElement element = driver.findElement(By.xpath(xpathValue));
				element.click();
				msg = true;
			}
			AjaxWait ajaxWait = new AjaxWait();
			ajaxWait.waitForAjax(driver, waitTime);
		} catch (Exception e) {
			msg = false;
			log.info("Throwable Exception in clickTextareaBox \n" + e.getMessage());
		}
		return msg;
	}

	/**
	 * 
	 * @param driver
	 * @param xpathValue
	 * @param waitTime
	 * @return
	 */
	public static boolean clearTextareaValue(WebDriver driver,String xpathValue, int waitTime) {
		boolean msg = false;
		try {
			if(isTextareaVisible(driver, xpathValue)){
				WebElement element = driver.findElement(By.xpath(xpathValue));
				element.clear();
				msg = true;
			}
			AjaxWait ajaxWait = new AjaxWait();
			ajaxWait.waitForAjax(driver, waitTime);
		} catch (Exception e) {
			msg = false;
			log.info("Throwable Exception in clearTextareaValue \n" + e.getMessage());
		}
		return msg;
	}

	/**
	 * 
	 * @param driver
	 * @param xpathValue
	 * @param waitTime
	 * @param textValue
	 * @param indexValue
	 * @return
	 */
	public static boolean selectMultipleTextValue(WebDriver driver,	String xpathValue, int waitTime,String textValue1,String textValue2,String textValue3) {
		boolean msg = false;
		try {
			Select selectVal = new Select(driver.findElement(By.xpath(xpathValue)));
			selectVal.selectByVisibleText(textValue1);
			selectVal.selectByVisibleText(textValue2);
			selectVal.selectByVisibleText(textValue3);
			msg = true;
			AjaxWait ajaxWait = new AjaxWait();
			ajaxWait.waitForAjax(driver, waitTime);
		} catch (Exception e) {
			msg = false;
			log.info("Throwable Exception in selectMultipleTextValue \n" + e.getMessage());
		}
		return msg;
	}

	/**
	 * 
	 * @param driver
	 * @param xpathValue
	 * @param waitTime
	 * @param textValue1
	 * @param textValue2
	 * @param textValue3
	 * @return
	 */
	public static boolean selectMultipleIndexValue(WebDriver driver, String xpathValue, int waitTime,String indexValue1,String indexValue2,String indexValue3) {
		boolean msg = false;
		try {
			Select selectVal = new Select(driver.findElement(By.xpath(xpathValue)));
			selectVal.selectByVisibleText(indexValue1);
			selectVal.selectByVisibleText(indexValue2);
			selectVal.selectByVisibleText(indexValue3);
			msg = true;
			AjaxWait ajaxWait = new AjaxWait();
			ajaxWait.waitForAjax(driver, waitTime);
		} catch (Exception e) {
			msg = false;
			log.info("Throwable Exception in selectMultipleIndexValue \n" + e.getMessage());
		}
		return msg;
	}



}
