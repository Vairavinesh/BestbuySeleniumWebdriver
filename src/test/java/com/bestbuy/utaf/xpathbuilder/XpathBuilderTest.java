package com.bestbuy.utaf.xpathbuilder;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.InputStream;

import org.junit.Before;
import org.junit.Test;

import com.bestbuy.utaf.exceptions.UTAFException;
import com.bestbuy.utaf.util.DriverProps;
/**
 * @author BestBuy Inc
 * Unit test case for XPathBuilder
 */
public class XpathBuilderTest {
	
	String propsFile = "src" + File.separatorChar + "test" + File.separatorChar + "resources" + File.separatorChar + "Driver_Properties" + File.separatorChar + "driverpoc_properties.xml";
	
	XpathBuilder xpathBuilder;
	
	
	@Before
	public void before() throws UTAFException {
		InputStream is = this.getClass().getClassLoader().getResourceAsStream("Driver_Properties/driverpoc_properties.xml");
		DriverProps driverProps = new DriverProps(is);
		xpathBuilder = new XpathBuilder(
				driverProps.getPropValue("xPathDefinitionFilePath")
				+ "xpathBuilderTestXpaths.xml");
	}
	
	/**
	 * Unit test for getElementXpath for list without passing element location
	 */
	@Test
	public void testGetElementXpath() throws UTAFException {
		String xPath = xpathBuilder.getElementXpath("mdot", null, "list","result");
		assertEquals("",xPath);
	}
	
	/**
	 * Unit test for getElementXpath for counter in header
	 */
	@Test
	public void testGetElementXpathForHeaderAndCounter() throws UTAFException {
		String xPath = xpathBuilder.getElementXpath("cart", "header", "counter","result");
		assertEquals("//*[contains(@id, 'header')]/*[@class='cart-counter']",xPath);
	} 
	
	/**
	 * Unit test for getElementXpath for counter in footer
	 */
	@Test
	public void testGetElementXpathForFooterAndCounter() throws UTAFException {
		String xPath = xpathBuilder.getElementXpath("cart", "footer", "counter","result");
		assertEquals("//*/footer/*/*[@class='cart-counter']",xPath);
	} 
	
	/**
	 * Unit test for getElementXpath for subtotal in header
	 */
	@Test
	public void testGetElementXpathForHeaderAndSubtotal() throws UTAFException {
		String xPath = xpathBuilder.getElementXpath("cart", "header", "subtotal","result");
		assertEquals("//*[contains(@id,'top')]//*[contains(@class,'subtotal')]\"",xPath);
	} 
	
	/**
	 * Unit test for getElementXpath for subtotal in footer
	 */
	@Test
	public void testGetElementXpathForFooterAndSubtotal() throws UTAFException {
		String xPath = xpathBuilder.getElementXpath("cart", "footer", "subtotal","result");
		assertEquals("//*[contains(@id,'bottom')]//*[contains(@class,'subtotal')]\"",xPath);
	} 
	
	/**
	 * Unit test for getElementXpath for text in header
	 */
	@Test
	public void testGetElementXpathForHeaderAndText() throws UTAFException {
		String xPath = xpathBuilder.getElementXpath("mdot", "header", "text","result");
		assertEquals("//*[contains(@id,'top')]//*[contains(@class, 'result')]",xPath);
	} 
	
	/**
	 * Unit test for Replace location for label in header
	 */
	@Test
	public void testReplaceLocation() throws UTAFException {
		String xPath = xpathBuilder.getElementXpath("mdot", "header", "label","result");
		assertEquals("//*[contains(@id,'top')]//*[contains(text(), 'result')]",xPath);
	} 

}
