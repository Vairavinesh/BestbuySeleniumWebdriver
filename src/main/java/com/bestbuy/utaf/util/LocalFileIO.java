package com.bestbuy.utaf.util;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.web.client.RestClientException;


public class LocalFileIO {

	private static Logger log = Logger.getLogger("LocalFileIO");
	private String targetDirectory = "/target" + "/surefire-reports"
			+ "/ScreenShots";
	private String currentdir;
	private String screenShotFolder;
	private File fileName;
	private String targetPath;

	/**
	 * List all files from a directory and its subdirectories
	 * @param directoryName to be listed
	 */
	public List<File> getAllFilesInDirTree(String pathname) {
		File f = new File(pathname);
		File[] listfiles = f.listFiles();
		List<File> fileList = new ArrayList<File>();
		for (File file:listfiles) {
			if (file.isDirectory()) {
				File[] internalFiles = file.listFiles();
				for (File internalFile:internalFiles) {
					if (internalFile.isDirectory()) {
						getAllFilesInDirTree(internalFile.getAbsolutePath());
					} else {
						fileList.add(internalFile);
					}
				}
			}else{
				fileList.add(file);
			}
		}
		log.info("File List : "+fileList);
		return fileList;
	}
	
	/**
	 * Get Current Directory
	 * @return String
	 */
	public String getCurrentDirectory() {
		currentdir = System.getProperty("user.dir");
		screenShotFolder = currentdir + targetDirectory;
		fileName = new File(screenShotFolder);
		if (!fileName.exists()) {
			fileName.mkdir();
		}
		targetPath = fileName.getAbsolutePath();
		return targetPath;
	}
	
	/**
	 * 
	 * @param fldrName
	 */
	public void crtFolder(String fldrName) {
		try {
			File fldr = new File(fldrName);
			if (!fldr.exists()) {
				fldr.mkdir();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * 
	 * @param file
	 * @param text
	 */
	
	public void crtFile(String file) {
		try {
			File xFile = new File(file);
			boolean exist = xFile.createNewFile();
			if (!exist) {
				log.info("File already exists. Deleting file: " + file);
			    xFile.delete();
			}
			log.info("File created successfully.");

		} catch (RestClientException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}