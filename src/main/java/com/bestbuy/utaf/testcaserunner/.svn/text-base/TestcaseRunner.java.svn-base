package com.bestbuy.utaf.testcaserunner;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;

import org.apache.log4j.Logger;
import org.xml.sax.SAXException;

import com.bestbuy.fta.basedataframework.common.exception.TDMServiceException;
import com.bestbuy.utaf.exceptions.UTAFException;
import com.bestbuy.utaf.util.DriverProps;
import com.bestbuy.utaf.util.UtafConstants;
import com.bestbuy.utaf.xmlparser.XMLParser;
import com.ximpleware.NavException;
import com.ximpleware.XPathEvalException;
import com.ximpleware.XPathParseException;

public class TestcaseRunner {
	private final static Logger log = Logger.getLogger(TestcaseRunner.class);
	
	private DriverProps drv;
	private List<String> msgs;
	private int waitTime;
	private int totalSteps;
	private XMLParser xmlParser;

	public List<String> getMsgs() {
		return msgs;
	}
	
	private void setInitilize() throws UTAFException{
		InputStream is = this.getClass().getClassLoader().getResourceAsStream("Driver_Properties/driverpoc_properties.xml");
		drv = new DriverProps(is);
		waitTime = Integer.parseInt(drv.getPropValue("ajaxwaittimeout"));
		msgs = new ArrayList<String>();
		xmlParser = new XMLParser();
	}
	
	public void buildAndExecuteTestCase(String testCaseName) throws UTAFException, IOException, XPathParseException, XPathEvalException, NavException, SAXException, ParserConfigurationException, XPathExpressionException, TDMServiceException{
		setInitilize();
		HashMap<String, String> browserResolutionMap = new HashMap<String, String>();
		browserResolutionMap.put("Firefox", null); 
		List<Map<String, String>> stepList = xmlParser.parseXML(testCaseName);
		totalSteps = stepList.size();
		log.info("Total Steps: " + totalSteps);
		this.execute(stepList, totalSteps, browserResolutionMap);
	}

	public void execute(List<Map<String, String>> stepLists, int totalSteps,Map<String, String> browserResolutionMap) throws  UTAFException, XPathParseException, XPathEvalException, NavException, ParserConfigurationException, SAXException, IOException, TDMServiceException{
		xmlParser.getEnvironment(drv.getPropValue("environmentConfig"),"QA1");
		TestcaseStepHandler stepHandler = new TestcaseStepHandler(drv,waitTime,xmlParser, "QA1");
		DataResourceHandler dRH = null;
		for (String browser : browserResolutionMap.keySet()) {
			String resolution = browserResolutionMap.get(browser);
			for(Map<String, String> step : stepLists){
				String msg = "";
				if (step.get(UtafConstants.CONTROL) != null && step.get(UtafConstants.CONTROL).equals(UtafConstants.DATAINPUT)) {
					dRH = new DataResourceHandler();
				}
				msg = stepHandler.stepControl(step, dRH,browser,resolution);
				if (msg != null && !msg.isEmpty()) {
					msgs.add(msg);
				}
				for (String m : msgs) {
					log.info(m);
				}
				msgs.clear();
			}
			stepHandler.getSeleniumCtrls().closeBrowser();
		}
	}
	
	/**
	 * Combine both Broswer list and resolution list to HashMap
	 * @param browserLists
	 * @param resolutionList
	 * @return HashMap<String, String>
	 * @throws Exception
	 */
	@SuppressWarnings("unused") //TO DO - Implement after UI sends browser and resolution
	private Map<String, String> combineBrowserAndResolution(List<String> browserLists, List<String> resolutionList) throws Exception{
		Map<String, String> browserResolutionMap = new HashMap<String, String>();
		if (browserLists.size() != resolutionList.size()){
			 throw new UTAFException("'keys' list and 'values' list differs in size");
		}
        for (int i = 0; i < browserLists.size(); i++){
            String newKey = browserLists.get(i);
            String newValue = resolutionList.get(i);
            browserResolutionMap.put(newKey, newValue);
        }
		return browserResolutionMap;
	}
	
	/**
	 * Get the keyset from hashmap and build a list
	 * @param browsers
	 * @return List<String>
	 */
	public List<String> getList(String browsers) {
		List<String> browserList = new ArrayList<String>();
		String[] browserArr = browsers.split(",");
		browserList = Arrays.asList(browserArr);
		browserArr = null;
		return  browserList;
	}

}
