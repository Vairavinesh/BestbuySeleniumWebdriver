package com.bestbuy.utaf.enumutil;

import org.openqa.selenium.WebDriver;

import com.bestbuy.utaf.browser.LaunchBrowser;

/**
 * Enum class for Browser
 * @author a922998
 *
 */
public enum SelectBrowserEnum {

	FIREFOX() {
		public WebDriver execute(String resolution) {
			return LaunchBrowser.firefoxBrowser(resolution);
		}

	},
	CHROME() {
		public WebDriver execute(String resolution) {
			return LaunchBrowser.chromeBrowser(resolution);
		}
	},
	SAFARI(){
		public WebDriver execute(String resolution) {
			return LaunchBrowser.safariBrowser(resolution);
		}
	},
	IPHONE(){
		public WebDriver execute(String resolution) {
			return LaunchBrowser.iphoneBrowser();
		}
	},
	ANDROID(){
		public WebDriver execute(String resolution) {
			return LaunchBrowser.androidBrowser();
		}
	},
	HTMLUNIT(){
		public WebDriver execute(String resolution) {
			return LaunchBrowser.htmlunitBrowser();
		}
	};

	public WebDriver execute(String resolution) {
		return null;

	}
}
