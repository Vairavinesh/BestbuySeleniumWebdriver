/**
 * 
 */
package com.bestbuy.utaf.testcaserunner;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.InvalidPropertiesFormatException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;

import org.junit.Before;
import org.junit.Test;
import org.xml.sax.SAXException;

import com.bestbuy.fta.basedataframework.common.exception.TDMServiceException;
import com.bestbuy.utaf.exceptions.UTAFException;
import com.bestbuy.utaf.util.DriverProps;
import com.ximpleware.NavException;
import com.ximpleware.XPathEvalException;
import com.ximpleware.XPathParseException;

/**
 * @author a922998
 *
 */
public class TestcaseRunnerTest {
	
	TestcaseRunner testcaseRunner;
	DriverProps driverProps;
	
	@Before
	public void init() throws InvalidPropertiesFormatException, UTAFException{
		testcaseRunner = new TestcaseRunner();
		InputStream is = this.getClass().getClassLoader().getResourceAsStream("Driver_Properties/driverpoc_properties.xml");
		driverProps = new DriverProps(is);
	}

	@Test
	public void test() throws XPathParseException, XPathEvalException, NavException, XPathExpressionException, UTAFException, IOException, SAXException, ParserConfigurationException, TDMServiceException {
		String testFile = "src/test/resources/xml/testcase/testcase.xml";
		File f1 = new File(testFile);
		testcaseRunner.buildAndExecuteTestCase(f1.getAbsolutePath());
	}
	
	@Test
	public void test1() throws IOException, XPathParseException, XPathEvalException, NavException, UTAFException, SAXException, ParserConfigurationException, XPathExpressionException, TDMServiceException {
		String testFile = "src/test/resources/xml/testcase/setTestCase.xml";
		File f1 = new File(testFile);
		testcaseRunner.buildAndExecuteTestCase(f1.getAbsolutePath());
	}
	
	@Test
	public void test2() throws IOException, XPathParseException, XPathEvalException, NavException, UTAFException, SAXException, ParserConfigurationException, XPathExpressionException, TDMServiceException {
		String testFile = "src/test/resources/xml/testcase/clickTestCase.xml";
		File f1 = new File(testFile);
		testcaseRunner.buildAndExecuteTestCase(f1.getAbsolutePath());
	}

}
