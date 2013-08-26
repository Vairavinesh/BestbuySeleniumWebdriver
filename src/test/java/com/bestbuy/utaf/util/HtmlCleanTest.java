/**
 * 
 */
package com.bestbuy.utaf.util;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.junit.Before;
import org.junit.Test;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import com.bestbuy.utaf.util.HtmlClean;

/**
 * @author a948621
 * 
 */
public class HtmlCleanTest {

	String testFilePass;
	String testFileCdata;
	HtmlClean cleaner;
	String outputPath = "target" + File.separatorChar + "cleaned_xml"
			+ File.separatorChar;

	@Before
	public void init() throws FileNotFoundException {
		testFilePass = "src/test/resources/VTDPageSourceParserTestData/mdothome.xml";
		testFileCdata = "src/test/resources/VTDPageSourceParserTestData/pdp.xml";
		cleaner = new HtmlClean();

		// vtdXMLParser= new VTDXMLParser(f1.getAbsolutePath());
		// pgDomParser = new PageSourceDomParser();
	}

	@Test
	public void testFileNotFoundException() throws IOException {
		boolean pass = false;
		try {
			cleaner.cleanHtml("noFile.xml", outputPath);
	
			
		} catch (FileNotFoundException e) {
			pass = true;
			// TODO Auto-generated catch block
			//e.printStackTrace();
		}
		assertTrue("FileNotFoundException exception thrown.", pass);
	}
	
	@SuppressWarnings("unused")
	@Test
	public void testCleanCData() throws IOException {
		boolean pass = true;
		String msg = "Clean Html successful. ";
		cleaner.cleanHtml(testFileCdata, outputPath);
		try {
			Document document;
			DocumentBuilder documentBuilder;
			DocumentBuilderFactory documentBuilderFactory;
			File xmlInputFile;
			xmlInputFile = new File(testFileCdata);
			documentBuilderFactory = DocumentBuilderFactory.newInstance();
			documentBuilder = documentBuilderFactory.newDocumentBuilder();
			document = documentBuilder.parse(xmlInputFile);
		} catch (SAXException e) {
			msg = e.getMessage();
			pass = false;
		} catch (ParserConfigurationException e) {
			msg = e.getMessage();
			pass = false;
		}
		assertFalse(msg, pass);
	}

	@SuppressWarnings("unused")
	@Test
	public void testCleanPass() throws IOException {
		boolean pass = true;
		String msg = "Clean Html successful. ";
		cleaner.cleanHtml(testFilePass, outputPath);
		try {
			Document document;
			DocumentBuilder documentBuilder;
			DocumentBuilderFactory documentBuilderFactory;
			File xmlInputFile;
			xmlInputFile = new File(testFilePass);
			documentBuilderFactory = DocumentBuilderFactory.newInstance();
			documentBuilder = documentBuilderFactory.newDocumentBuilder();
			document = documentBuilder.parse(xmlInputFile);
		} catch (SAXException e) {
			msg = e.getMessage();
			pass = false;
		} catch (ParserConfigurationException e) {
			msg = e.getMessage();
			pass = false;
		}
		assertTrue(msg, pass);
	}

}
