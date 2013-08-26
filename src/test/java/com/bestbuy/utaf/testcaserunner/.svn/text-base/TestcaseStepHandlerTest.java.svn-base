package com.bestbuy.utaf.testcaserunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;

import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.lang.StringUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.xml.sax.SAXException;

import com.bestbuy.fta.basedataframework.common.exception.TDMServiceException;
import com.bestbuy.utaf.exceptions.UTAFException;
import com.bestbuy.utaf.util.DriverProps;
import com.bestbuy.utaf.xmlparser.XMLParser;
import com.ximpleware.NavException;
import com.ximpleware.XPathEvalException;
import com.ximpleware.XPathParseException;

/**
 * @author a948621
 * 
 */
public class TestcaseStepHandlerTest {

	StepValidator stepValidator;
	RunStep runStep;
	DriverProps drv;
	SeleniumCtrls seleniumCtrls;
	int waitTime = 30;
	TestcaseStepHandler stepHandler;
	DataResourceHandler dRH;
	XMLParser xmlParser;

	String testSelectableURLXML = "<?xml version='1.0' encoding='UTF-8'?><testcase xmlns:xsi='http://www.w3.org/2001/XMLSchema-instance' xsi:noNamespaceSchemaLocation='src/test/resources/Tests/schemas/Function.xsd'><test application='cart' function='true' create_date='2013-7-18' created_by='' functional_group='search' functional_subgroup=''id='TestAddToCart.xml' last_updated='2013-7-18' updated_by='' first_version='' last_version=''>Testcase</test> <teststeps> <step control='comment' number='1'>Alternate start URL</step> <step control='browser' action='select_url' url='pdp_url' data_input='sku_5578649.xml' data_key='sku' number='2'></step> <step page_name='addtocart' action='click' element_type='button' element_name='AddToCart' number='3'> </step> <step action='validate_pagetitle' page_name='mdot' number='4' element_type='text' element_name='pageTitle' text='Added to Your Cart'></step> <step action='validate_text' page_name='mdot' number='5' element_type='text' element_name='basePrice' text='$529.99'></step> </teststeps> </testcase>";
	HashMap<String, String> stepControl;
	
	String dataRsc = "<?xml version='1.0' encoding='UTF-8'?><datareource xmlns:xsi='http://www.w3.org/2001/XMLSchema-instance'	xsi:noNamespaceSchemaLocation='DataResourceSchema.xsd'>	<description resource_type='sku' dataType='hardcoded' function='group1'/> <data> <sku>5578649</sku>	<productTag> Samsung - 40&quot; Class (40&quot; Diag.) - LED - 1080p - 60Hz - Smart - HDTV</productTag>	</data></datareource>";
	
	@Before
	public void setup() throws IOException, SAXException, ParserConfigurationException, UTAFException {
		InputStream is = this.getClass().getClassLoader().getResourceAsStream("Driver_Properties/driverpoc_properties.xml");
		drv = new DriverProps(is);
		xmlParser = new XMLParser();
		xmlParser.getEnvironment(drv.getPropValue("environmentConfig"),"QA1");
		stepHandler = new TestcaseStepHandler(drv, waitTime, xmlParser, "QA1");
		dRH =  new DataResourceHandler();
	}
	
	@After
	public void destroy(){
		stepControl.clear();
	}

	@Test
	public void testStepControlComment() throws UTAFException, TDMServiceException, XPathParseException, XPathEvalException, NavException{
		stepControl = new HashMap<String, String>();
		stepControl.put("control", "comment");
		stepControl.put("comment", "Test Add to cart");
		dRH = new DataResourceHandler();
		String message = stepHandler.stepControl(stepControl, dRH, "firefox", null);
		assertEquals("Test Add to cart",message);
	}
	
	@Test
	public void testStepControlURL() throws UTAFException, TDMServiceException, XPathParseException, XPathEvalException, NavException{
		stepControl = new HashMap<String, String>();
		stepControl.put("control", "browser");
		stepControl.put("action", "select_url");
		stepControl.put("url", "mdothome_url");
		dRH = new DataResourceHandler();
		String message = stepHandler.stepControl(stepControl, dRH, "firefox", null);
		assertTrue(StringUtils.isEmpty(message));
		stepHandler.getSeleniumCtrls().destroy();
	}
	
	@Test
	public void testStepFunctionStartComment() throws UTAFException, TDMServiceException, XPathParseException, XPathEvalException, NavException{
		stepControl = new HashMap<String, String>();
		stepControl.put("control", "comment");
		stepControl.put("comment", "Starting Function:/Users/a922998/objectdb_bk/kalai/xml/function/AddToCart.xml");
		dRH = new DataResourceHandler();
		String message = stepHandler.stepControl(stepControl, dRH, "firefox", null);
		assertEquals("Starting Function:/Users/a922998/objectdb_bk/kalai/xml/function/AddToCart.xml",message);
	}
	
	@Test
	public void testStepDataResource() throws UTAFException, TDMServiceException, XPathParseException, XPathEvalException, NavException{
		stepControl = new HashMap<String, String>();
		stepControl.put("data_inputs", "/Users/a922998/objectdb_bk/kalai/xml/dataresource/dr_sku_5578649.xml");
		stepControl.put("control", "data_input");
		stepControl.put("data_types", "hardcoded");
		dRH = new DataResourceHandler();
		String message = stepHandler.stepControl(stepControl, dRH, "firefox", null);
		assertEquals("DataResource Built Successfully",message);
	}
	
	@Test @Ignore
	public void testStepFunctionStopComment() throws UTAFException, TDMServiceException, XPathParseException, XPathEvalException, NavException{
		testStepDataResource();
		stepControl = new HashMap<String, String>();
		stepControl.put("control", "comment");
		stepControl.put("comment", "Stopping Function:/Users/a922998/objectdb_bk/kalai/xml/function/AddToCart.xml");
		dRH = new DataResourceHandler();
		String message = stepHandler.stepControl(stepControl, dRH, "firefox", null);
		assertEquals("Stopping Function:/Users/a922998/objectdb_bk/kalai/xml/function/AddToCart.xml",message);
	}

}
