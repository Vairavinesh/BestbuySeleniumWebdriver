package com.bestbuy.utaf.browser;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.android.AndroidDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.iphone.IPhoneDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.Dimension;
import com.gargoylesoftware.htmlunit.BrowserVersion;


public class LaunchBrowser {

	private static WebDriver driver;
	private static HtmlUnitDriver htmlDriver;
	private static Logger log = Logger.getLogger("LaunchBrowser");

	/**
	 * Launch Firefox browser
	 * @param resolution
	 * @return WebDriver
	 */
	public static WebDriver firefoxBrowser(String resolution) {
		log.info("LAUNCHING FIREFOX BROWSER");		
		if (OSFinder.isUnix()) {
			System.setProperty("webdriver.firefox.bin",
					"/usr/local/bin/firefox701_for_fta");
		}
		driver = new FirefoxDriver();
		windowResize(resolution);
		return driver;
	}

	/**
	 * Launch chrome browser
	 * @param resolution
	 * @return WebDriver
	 */
	public static WebDriver chromeBrowser(String resolution) {
		log.info("LAUNCHING GOOGLE CHROME BROWSER");
		if (OSFinder.isMac()) {
			System.setProperty("webdriver.chrome.driver",
					"./src/main/resources/SeleniumDrivers/chromedriver");
			driver = new ChromeDriver();
		} else if (OSFinder.isWindows()) {
			System.setProperty("webdriver.chrome.driver",
					"./src/main/resources/chromedriver/windows/chromedriver.exe");
			driver = new ChromeDriver();
		} else {
			System.getProperties().put("webdriver.chrome.bin",
					"/usr/local/bin/chrome_for_fta");			
			driver = new ChromeDriver();
		}
		windowResize(resolution);
		return driver;
	}

	/**
	 * Launch Safari browser
	 * @param resolution
	 * @return WebDriver
	 */
	public static WebDriver safariBrowser(String resolution) {
		log.info("LAUNCHING SAFARI BROWSER");
		driver = new SafariDriver();
		windowResize(resolution);
		return driver;
	}

	/**
	 * Launch Iphone browser 
	 * @return WebDriver
	 */
	public static WebDriver iphoneBrowser() {
		log.info("LAUNCHING IPHONE BROWSER");
		try {
			driver = new IPhoneDriver();
		} catch (Exception exception) {
			log.warning("Exception occurred while launching iphone browser"+exception.getMessage());
		}
		return driver;
	}

	/**
	 * Launch AndroidBrowser
	 * @return WebDriver
	 */
	public static WebDriver androidBrowser() {
		log.info("LAUNCHING ANDROID BROWSER");
		driver = new AndroidDriver();
		return driver;
	}

	/**
	 * Launch html unit browser
	 * @return WebDriver
	 */
	@SuppressWarnings("deprecation")
	public static WebDriver htmlunitBrowser() {
		log.info("LAUNCHING HTML UNIT BROWSER");
		if (OSFinder.isUnix()) {
			System.setProperty("webdriver.firefox.bin",
					"/usr/local/bin/firefox701_for_fta");
		}
		htmlDriver = new HtmlUnitDriver(BrowserVersion.FIREFOX_10);
		htmlDriver.setJavascriptEnabled(true);
		java.util.logging.Logger.getLogger("com.gargoylesoftware").setLevel(
				Level.OFF);
		return htmlDriver;
	}

	/**
	 * Resize the window
	 * @param resolution
	 */
	private static void windowResize(String resolution) {
		if (resolution != null) {
			String[] tokens = resolution.split("x");
			String resolutionX = tokens[0];
			String resolutionY = tokens[1];
			int x = Integer.parseInt(resolutionX);
			int y = Integer.parseInt(resolutionY);
			Dimension screenResolution = new Dimension(x, y);
			driver.manage().window().setSize(screenResolution);
		} else {
			driver.manage().window().maximize();
		}
	}

	/**
	 * Destroy Browser
	 */
	public void destroy() {
		driver.close();
	}

}
