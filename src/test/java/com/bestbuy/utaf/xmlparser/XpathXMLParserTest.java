package com.bestbuy.utaf.xmlparser;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.junit.Before;
import org.junit.Test;
import org.xml.sax.SAXException;

import com.bestbuy.utaf.exceptions.UTAFException;

/**
 * @author BestBuy Inc
 *
 */
public class XpathXMLParserTest {

	XpathXMLParser xpathXMLParser;
	String xPath;
	
	@Before
	public void init() throws FileNotFoundException {
		String testFile = "src/test/resources/xPath_Definitions/mdotxpaths.xml";
		File f1 = new File(testFile);
		xpathXMLParser= new XpathXMLParser(f1.getAbsolutePath());
		xPath = "//*/step";
	}
	
	@Test
	public void testGetXpathForTextBox() throws UTAFException {
		String nextStep = xpathXMLParser.getXpath("mdothome", "textbox");
		assertEquals("//*/~locationVar~/*/input[@id='textfield' and contains(@class, '~idVar~')]", nextStep);
	}
	
	@Test
	public void testGetXpath() throws  UTAFException {
		String nextStep = xpathXMLParser.getXpath("common", "label");
		assertEquals("",nextStep);
	}
	
	@Test
	public void testGetXpathForContentMeta() throws  UTAFException {
		String nextStep = xpathXMLParser.getXpath("mdothome", "contentmeta");
		assertEquals("//*/a[@class='content_block']//*/div[contains(@class, 'link') and contains(translate(., 'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'), '~nameVar~')]/..//*/div[contains(@class, 'meta')]", nextStep);
	}
	
	@Test
	public void testGetXpathForElseCond() throws UTAFException {
		String nextStep = xpathXMLParser.getXpath("common", "textbox");
		assertEquals("//*/~locationVar~/*/input[@id='textfield' and contains(@class, '~idVar~')]",nextStep);
	}
	
	@Test
	public void testGetXpathInvalid() throws UTAFException{
		String nextStep = xpathXMLParser.getXpath("mdothome", "erdfs");
		assertEquals("",nextStep);
	}
	
	@Test
	public void testGetXpathAttribCommon() throws UTAFException{
		String nextStep = xpathXMLParser.getXpathAttrib("common", "textbox", "headerLoc");
		assertEquals("div[contains(@id, 'header')]/", nextStep);
	}
	
	@Test
	public void testGetXpathAttrib() throws UTAFException{
		String nextStep = xpathXMLParser.getXpathAttrib("mdothome", "textbox", "headerLoc");
		assertEquals("div[contains(@id, 'header')]/", nextStep);
	}
	
	@Test
	public void testGetXpathAttribInvalid() throws UTAFException{
		String nextStep = xpathXMLParser.getXpathAttrib("mdothome", "erdfs", "headerLoc");
		assertEquals("",nextStep);
	}
	
	@Test(expected=SAXException.class)
	public void testGetElementSAXException() throws UTAFException{
		String testFile = "src/test/resources/xPath_Definitions/mdotxpathsInvalid.xml";
		File f1 = new File(testFile);
		xpathXMLParser= new XpathXMLParser(f1.getAbsolutePath());
		xPath = "//*/step";
		xpathXMLParser.getXpathAttrib("mdothome", "erdfs", "headerLoc");
	}
	
	@Test(expected=IOException.class)
	public void testGetElementParseException() throws UTAFException{
		String testFile = "src/test/resources/xPath_Definitions/Invalid.xml";
		File f1 = new File(testFile);
		xpathXMLParser= new XpathXMLParser(f1.getAbsolutePath());
		xPath = "//*/step";
		xpathXMLParser.getXpathAttrib("mdothome", "erdfs", "headerLoc");
	}
	
	@Test
	public void testGetXpathExpectedResult() throws UTAFException {
		String testFile = "src/test/resources/Tests/TestDataForVtdTestcaseParserTest.xml";
		File f1 = new File(testFile);
		xpathXMLParser= new XpathXMLParser(f1.getAbsolutePath());
		xPath = "//*/step";
		String nextStep = xpathXMLParser.getXpath("common", "label");
		assertEquals("",nextStep);
	}
}
