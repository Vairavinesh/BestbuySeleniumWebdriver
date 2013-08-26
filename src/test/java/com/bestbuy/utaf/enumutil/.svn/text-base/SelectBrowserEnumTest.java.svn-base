package com.bestbuy.utaf.enumutil;

import static org.junit.Assert.assertNotNull;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.xml.sax.SAXException;

import com.bestbuy.utaf.browser.LaunchBrowser;
/**
 * @author Kalaiselvi Jaganathan
 *
 */
public class SelectBrowserEnumTest {
	
	SelectBrowserEnum selectBrowserEnum;
	
	LaunchBrowser launchBrowser;
	
	@Before
	public void init() {
		launchBrowser = new LaunchBrowser();
	}

	@SuppressWarnings("static-access")
	@Test
	public void testFirefox() throws ParserConfigurationException, SAXException, IOException {
		WebDriver message = selectBrowserEnum.valueOf("FIREFOX").execute("480x640");
		assertNotNull(message);
		launchBrowser.destroy();
	}
	
	@SuppressWarnings("static-access")
	@Test @Ignore
	public void testSafari() throws ParserConfigurationException, SAXException, IOException {
		WebDriver message = selectBrowserEnum.valueOf("SAFARI").execute("480x640");
		assertNotNull(message);
		launchBrowser.destroy();
	}

}
