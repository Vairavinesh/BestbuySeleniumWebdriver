package com.bestbuy.utaf.pagesource;

import java.util.List;


/**
 * @author a1013656
 *
 */

import junit.framework.Assert;

import org.junit.BeforeClass;
import org.junit.Test;

public class GetPageSourceTest {
	
	static GetPageSource pageSource;
	
	@BeforeClass
	public static void setup(){
		pageSource = new GetPageSource();
		
	}

	@SuppressWarnings("static-access")
	@Test
	public void testProperResponsecode(){
	List<Integer> reposecode =	pageSource.pageSourceData();
	int response = 0;
	if (reposecode.contains(200)) {
		response = 200;
		
	}
	Assert.assertEquals(200,response);
	Assert.assertTrue(reposecode.contains(200));
	}
	
	@SuppressWarnings("static-access")
	@Test
	public void testImproperResponsecode(){
	List<Integer> reposecode =	pageSource.pageSourceData();
	Assert.assertFalse(reposecode.contains(100));
	}

}
