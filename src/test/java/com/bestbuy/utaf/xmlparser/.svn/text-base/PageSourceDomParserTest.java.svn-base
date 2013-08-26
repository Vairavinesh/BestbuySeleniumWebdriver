/**
 * 
 */
package com.bestbuy.utaf.xmlparser;


import static org.junit.Assert.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.xpath.XPathExpressionException;

import org.apache.commons.lang3.StringEscapeUtils;
import org.junit.Before;
import org.junit.Test;
import org.xml.sax.SAXException;
import com.ximpleware.NavException;


/**
 * @author a948621
 *
 */
public class PageSourceDomParserTest {
	PageSourceDomParser pgDomParser;

	String testFile;
	String pageId = "mdothome";
	String appString = "mdot";
	String outputPathString = "target" + File.separatorChar;
	@Before
	public void init() throws FileNotFoundException {
		testFile = "src/test/resources/VTDPageSourceParserTestData/mdothome.xml";
		new File(testFile);
		
		//vtdXMLParser= new VTDXMLParser(f1.getAbsolutePath());
		 pgDomParser = new PageSourceDomParser();
	}
	
	@Test
	public void testGetMetaData() throws ParserConfigurationException, SAXException, IOException, XPathExpressionException, NavException, TransformerException {
		List<String> links = Arrays.asList("Cart", "Branding", "Popular Today", "Recommended Products","Recently Viewed Products","Menu","Search", 
											"Stores", "My Account", "LOW PRICE", "Weekly Deals", "Featured Offers", "Home", "Sign In", 
											"BBY Credit Cards", "View Order Status", "Gift Card Balance", "Help", "Terms", "Privacy", 
											"California Privacy Rights", "Feedback", "Desktop Site" , "Deal of the Day", "LOW PRICE GUARANTEE › We'll match prices on qualifying products.");
		pgDomParser.getElementMetaData(appString, testFile, "//*[@href]", "link", "mdothome",  outputPathString, "link");
//		for (String link : elementList) {
//			System.out.println(link);
//			assertTrue("metadata found for " + link + " in expected result list. ", links.contains(link));
//		}
	}
	


}
