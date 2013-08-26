package com.bestbuy.utaf.pagesource;

import junit.framework.Assert;

import org.junit.BeforeClass;
import org.junit.Test;

/**
 * @author a1013656
 *
 */


@SuppressWarnings("static-access")
public class GetCurrentDirTest {
	
	static GetCurrentDir currentDir;
	@BeforeClass
	public static void setup(){
		currentDir = new GetCurrentDir();
		
	}
	
	
	@Test
	public void createScreenShotLocation(){
		try {
			String Location = currentDir.getScreenShotCurrentDirectory();
			Assert.assertTrue(Location.contains("screenshots"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	@Test
	public void createPagesourceLocation(){
		try {
			String Location = currentDir.getPageSrcCurrentDirectory();
			Assert.assertTrue(Location.contains("pagesource"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	@Test
	public void createHiddenLocation(){
		try {
			String Location = currentDir.getHiddenPageSrcDirectory();
			Assert.assertTrue(Location.contains("hidden"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	@Test
	public void createTooltipLocation(){
		try {
			String Location = currentDir.getTooltipPageSrcDirectory();
			Assert.assertTrue(Location.contains("tooltip"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
//	@Test
//	public void createPaginationLocation(){
//		try {
//			String Location = currentDir.getPaginationCurrentDirectory();
//			Assert.assertTrue(Location.contains("pagination"));
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		
//	}

}
