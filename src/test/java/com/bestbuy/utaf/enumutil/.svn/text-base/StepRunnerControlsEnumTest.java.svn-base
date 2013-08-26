package com.bestbuy.utaf.enumutil;

import static org.junit.Assert.assertEquals;

import java.io.FileNotFoundException;
import java.util.HashMap;

import org.junit.Ignore;
import org.junit.Test;

import com.bestbuy.utaf.testcaserunner.DataResourceHandler;
import com.ximpleware.NavException;
import com.ximpleware.XPathEvalException;
import com.ximpleware.XPathParseException;

/**
 * @author BestBuy Inc
 *
 */
public class StepRunnerControlsEnumTest {

	StepRunnerControlsEnum stepRunnerControlsEnum;
	
	@SuppressWarnings("static-access")
	@Test
	public void testComment() throws FileNotFoundException, XPathParseException, XPathEvalException, NavException {
		HashMap<String, String> stepControls = new HashMap<String, String>();
		stepControls.put("comment", "data_input");
		String message = stepRunnerControlsEnum.valueOf("COMMENT").execute(stepControls,null,null,null);
		assertEquals("data_input",message);	
	}
	
	@Test
	public void testBrowser() throws FileNotFoundException, XPathParseException, XPathEvalException, NavException {
		HashMap<String, String> stepControls = new HashMap<String, String>();
		stepControls.put("comment", "data_input");
		String message = StepRunnerControlsEnum.valueOf("BROWSER").execute(stepControls,null,null,null);
		assertEquals(null,message);	
	}
	
	@Test
	public void testRestService() throws FileNotFoundException, XPathParseException, XPathEvalException, NavException {
		HashMap<String, String> stepControls = new HashMap<String, String>();
		stepControls.put("comment", "data_input");
		String message = StepRunnerControlsEnum.valueOf("REST_SERVICE").execute(stepControls,null,null,null);
		assertEquals(null,message);	
	}
	
	@Test
	public void testDBService() throws FileNotFoundException, XPathParseException, XPathEvalException, NavException {
		HashMap<String, String> stepControls = new HashMap<String, String>();
		stepControls.put("comment", "data_input");
		String message = StepRunnerControlsEnum.valueOf("DB_SERVICE").execute(stepControls,null,null,null);
		assertEquals(null,message);	
	}
	
	@Ignore
	@Test
	public void testDataInput() throws FileNotFoundException, XPathParseException, XPathEvalException, NavException {
		HashMap<String, String> stepControls = new HashMap<String, String>();
		stepControls.put("comment", "data_input");
		String message = StepRunnerControlsEnum.valueOf("DATA_INPUT").execute(stepControls,null,null,new DataResourceHandler());
		assertEquals(null,message);	
	}


}
