package com.bestbuy.utaf.testcaserunner;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.bestbuy.utaf.enumutil.SelectBrowserEnum;

/**
 * @author Kalaiselvi Jaganathan
 *
 */
public class SeleniumCtrlsTest {
	
	SeleniumCtrls seleniumCtrls;
	
	@Before
	public void init(){
		seleniumCtrls = new SeleniumCtrls(SelectBrowserEnum.FIREFOX, "http://m.qa.bestbuy.com/m/e", null, 0);
	}

	@Test @Ignore
	public void testGetCurrentUrl() {
		String url = seleniumCtrls.getCurrentUrl();
		assertTrue(url.contains("http://m.qa.bestbuy.com/m/e"));
		seleniumCtrls.destroy();
	}

}
