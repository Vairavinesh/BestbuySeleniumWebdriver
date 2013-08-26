package com.bestbuy.utaf.browser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

/**
 * @author Kalaiselvi Jaganathan
 *
 */
public class OSFinderTest {
	
	OSFinder oSFinder;
	
	@Before
	public void init() {
		oSFinder = new OSFinder();
	}

	@SuppressWarnings("static-access")
	@Test
	public void testMac() {
		oSFinder.setOS("mac");
		boolean systemType = oSFinder.isMac();
		assertTrue(systemType);
	}
	
	@SuppressWarnings("static-access")
	@Test
	public void testNotMac() {
		oSFinder.setOS("win");
		boolean systemType = oSFinder.isMac();
		assertFalse(systemType);
	}
	
	@SuppressWarnings("static-access")
	@Test
	public void testWindows() {
		oSFinder.setOS("win");
		boolean type = oSFinder.isWindows();
		assertTrue(type);
	}
	
	@SuppressWarnings("static-access")
	@Test
	public void testNotWindows() {
		oSFinder.setOS("mac");
		boolean type = oSFinder.isWindows();
		assertFalse(type);
	}
	
	@SuppressWarnings("static-access")
	@Test
	public void testNotSolaris() {
		oSFinder.setOS("mac");
		boolean type = oSFinder.isSolaris();
		assertFalse(type);
	}
	
	@SuppressWarnings("static-access")
	@Test
	public void testSolaris() {
		oSFinder.setOS("sunos");
		boolean type = oSFinder.isSolaris();
		assertTrue(type);
	}
	
	@SuppressWarnings("static-access")
	@Test
	public void testUnix1() {
		oSFinder.setOS("nix");
		boolean type = oSFinder.isUnix();
		assertTrue(type);
	}
	
	@SuppressWarnings("static-access")
	@Test
	public void testUnix2() {
		oSFinder.setOS("nux");
		boolean type = oSFinder.isUnix();
		assertTrue(type);
	}
	
	@SuppressWarnings("static-access")
	@Test
	public void testUnix3() {
		oSFinder.setOS("aix");
		boolean type = oSFinder.isUnix();
		assertTrue(type);
	}
	
	@SuppressWarnings("static-access")
	@Test
	public void testNotUnix() {
		oSFinder.setOS("mac");
		boolean type = oSFinder.isUnix();
		assertFalse(type);
	}
	
	@SuppressWarnings("static-access")
	@Test
	public void testGetOS(){
		oSFinder.setOS("mac");
		String OSValue = oSFinder.getOS();
		assertEquals("mac",OSValue);
	}
}
