package com.bestbuy.utaf.pagesource;

import java.io.File;
import java.util.logging.Logger;

public class GetCurrentDir {

	private static String target = "/target";
	private static String pageSrcTargetDirectory = target+ "/pageDetails" + "/pagesource";
	private static String hiddenDirectory = target + "/pageDetails"+ "/hidden";
	private static String toolTipDirectory = target+ "/pageDetails" + "/tooltip";
	private static String lightBoxDirectory = target+ "/pageDetails" + "/lightbox";
	private static String paginationDirectory = target+ "/pageDetails" + "/pagination";
	private static String currentdir = System.getProperty("user.dir");
	private static String targetDirectory = target + "/surefire-reports"
			+ "/screenshots";
	private static String screenShotFolder;
	private static String targetPath;
	
	private static Logger log = Logger.getLogger("GetCurrentDir");

	

	public static String getScreenShotCurrentDirectory()  {
		screenShotFolder = currentdir + targetDirectory;
		File fileName = new File(screenShotFolder);
		if (fileName.exists()) {
			log.info("folder structure " + fileName + "exists");
		} else {
			fileName.mkdirs();
			log.info("folder structure " + fileName + "created");
		}
		targetPath = fileName.getAbsolutePath();
		return targetPath;
	}

	public static String getHiddenPageSrcDirectory() {
		String hiddenSource = currentdir + hiddenDirectory;
		File fileName = new File(hiddenSource);
		if (!fileName.exists()) {
			fileName.mkdirs();
		}
		return fileName.getAbsolutePath();
	}

	public static String getTooltipPageSrcDirectory() {
		String tooltipLocation = currentdir + toolTipDirectory;
		File fileName = new File(tooltipLocation);
		if (!fileName.exists()) {
			fileName.mkdirs();
		}
		return fileName.getAbsolutePath();
	}

	public static String getLightBoxDirectory() {
		String lightBoxlocation = currentdir + lightBoxDirectory;
		File fileName = new File(lightBoxlocation);
		if (!fileName.exists()) {
			fileName.mkdirs();
		}
		return fileName.getAbsolutePath();
	}
	
	public static String getPageSrcCurrentDirectory() {
		String pageSourceFolder = currentdir + pageSrcTargetDirectory;
		File fileName = new File(pageSourceFolder);
		if (!fileName.exists()) {
			fileName.mkdirs();
		}
		return fileName.getAbsolutePath();
	}

	public static String getPaginationCurrentDirectory() {
		String pageSourceFolder = currentdir + paginationDirectory;
		File fileName = new File(pageSourceFolder);
		if (!fileName.exists()) {
			fileName.mkdirs();
		}
		return fileName.getAbsolutePath();
	}
	

}