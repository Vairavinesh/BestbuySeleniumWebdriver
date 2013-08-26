package com.bestbuy.utaf.browser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;

/**
 * @author Kalaiselvi Jaganathan
 *
 */
public class LaunchBrowserTest {

	LaunchBrowser launchBrowser;
	
	@Before
	public void init(){
		launchBrowser = new LaunchBrowser();
	}

	@SuppressWarnings("static-access")
	@Test
	public void testWindowResolution() {
		WebDriver driver = launchBrowser.firefoxBrowser("480x640");
		assertNotNull(driver);
		Dimension size = driver.manage().window().getSize();
		assertEquals(640, size.getHeight());
		assertEquals(480, size.getWidth());
		launchBrowser.destroy();
	}
	
	@SuppressWarnings("static-access")
	@Test
	public void testWindowResolutionMaximize() {
		WebDriver driver = launchBrowser.firefoxBrowser(null);
		Dimension size = driver.manage().window().getSize();
		assertNotNull(size.getHeight());
		assertNotNull(size.getWidth());
		assertNotNull(driver);
		launchBrowser.destroy();
	}

}
