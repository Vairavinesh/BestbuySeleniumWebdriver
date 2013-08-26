package com.bestbuy.utaf.browser;


/**
 * Find the Operating system
 * @author BestBuy Inc
 *
 */
public class OSFinder {

	private static String OS = System.getProperty("os.name").toLowerCase();

	/**
	 * @return the oS
	 */
	public static String getOS() {
		return OS;
	}

	/**
	 * @param oS the oS to set
	 */
	public static void setOS(String oS) {
		OS = oS;
	}

	public static boolean isWindows() {
		return (OS.indexOf("win") >= 0);
	}

	public static boolean isMac() {
		return (OS.indexOf("mac") >= 0);
	}

	public static boolean isUnix() {
		return (OS.indexOf("nix") >= 0 || OS.indexOf("nux") >= 0 || OS
				.indexOf("aix") >= 0);
	}

	public static boolean isSolaris() {
		return (OS.indexOf("sunos") >= 0);
	}
}
