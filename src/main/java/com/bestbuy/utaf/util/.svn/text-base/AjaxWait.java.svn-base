package com.bestbuy.utaf.util;

import org.apache.log4j.Logger;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

/**
 * 
 * @author BestBuy Inc
 *
 */
public class AjaxWait {

	private static Logger log = Logger.getLogger("AjaxWait");

	/**
	 * Check for active ajax call and wait
	 * @param driver
	 * @param timeoutInSeconds
	 */
	public void waitForAjax(WebDriver driver, int timeoutInSeconds) {
		log.info("Checking active ajax calls by calling jquery.active");
		try {
			if (driver instanceof JavascriptExecutor) {
				JavascriptExecutor jsDriver = (JavascriptExecutor) driver;

				for (int i = 0; i < timeoutInSeconds; i++) {
					Object numberOfAjaxConnections = jsDriver
							.executeScript("return jQuery.active");
					if (numberOfAjaxConnections instanceof Long) { 
						Long n = (Long) numberOfAjaxConnections;
						log.info("Number of active jquery ajax calls: " + n); 
						if (n.longValue() == 0L)
							break;
					}
					Thread.sleep(1000);
				}
			} else {
				log.info("Web driver: " + driver + " cannot execute javascript");
			}
		} catch (InterruptedException excep) {
			log.error("Exception occurred : "+excep.getMessage());
		}
	}
}
