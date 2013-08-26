package com.bestbuy.utaf.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.List;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

/**
 * @author BestBuy Inc
 *
 */
public class LocalFileIOTest {
	 
	LocalFileIO localFileIO;
	
	@Before
	public void init(){
		localFileIO = new LocalFileIO();
	}

	@Test
	public void testSubDirectory() {
		 List<File> lists = localFileIO.getAllFilesInDirTree("src/test/resources/Functions/group1");
		 assertEquals(16,lists.size());
	}
	
	@Test
	public void test2LvlSubDirectory() {
		 List<File> lists = localFileIO.getAllFilesInDirTree("src/test/resources/Tests");
		 assertEquals(19,lists.size());
	}

	@Test
	public void testGetCurrentDirectoryFileExist(){
		String dirPath = localFileIO.getCurrentDirectory();
		assertTrue(dirPath.contains("target/surefire-reports/ScreenShots"));
	}
	
	@Test @Ignore
	public void testGetCurrentDirectory(){
		String currentdir = System.getProperty("user.dir") + "/target" + "/surefire-reports"+ "/ScreenShots";
		File fileName = new File(currentdir);
		fileName.delete();
		String dirPath = localFileIO.getCurrentDirectory();
		assertTrue(dirPath.contains("target/surefire-reports/ScreenShots"));
	}
}
