package com.bestbuy.utaf.testcaserunner;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.lang.StringUtils;
import org.xml.sax.SAXException;

import com.bestbuy.fta.basedataframework.common.exception.TDMServiceException;
import com.bestbuy.fta.basedataframework.xsd.XMLIncludeBuilder;
import com.bestbuy.utaf.enumutil.ActionControlsEnum;
import com.bestbuy.utaf.enumutil.DataTypeSelectorEnum;
import com.bestbuy.utaf.enumutil.SelectBrowserEnum;
import com.bestbuy.utaf.enumutil.StepRunnerControlsEnum;
import com.bestbuy.utaf.exceptions.UTAFException;
import com.bestbuy.utaf.util.DriverProps;
import com.bestbuy.utaf.util.UtafConstants;
import com.bestbuy.utaf.xmlparser.XMLParser;
import com.bestbuy.utaf.xpathbuilder.XpathBuilder;
import com.ximpleware.NavException;
import com.ximpleware.XPathEvalException;
import com.ximpleware.XPathParseException;

public class TestcaseStepHandler {

	private StepValidator stepValidator;
	private RunStep runStep;
	private List<String> functionList = new ArrayList<String>();
	private DriverProps drv;
	private SeleniumCtrls seleniumCtrls;
	private int waitTime;
	private XMLParser xmlParser;
	private XpathBuilder xpathBuilder;
	private String env;
	
	/**
	 * @return the seleniumCtrls
	 */
	public SeleniumCtrls getSeleniumCtrls() {
		return seleniumCtrls;
	}

	TestcaseStepHandler(DriverProps drv, int waitTime, XMLParser xmlParser, String env){
		this.drv = drv;
		this.waitTime = waitTime;
		this.xmlParser = xmlParser;
		xpathBuilder = new XpathBuilder(drv.getPropValue("xPathDefinitionFilePath"));
		this.env = env;
	}

	/**
	 * Prepares a single step to be run
	 * 
	 * @param stepControls
	 * @param dRH
	 * @param browser
	 * @param resolution
	 * @return String
	 * @throws TDMServiceException 
	 * @throws IOException 
	 * @throws NavException 
	 * @throws XPathEvalException 
	 * @throws XPathParseException 
	 * @throws SAXException 
	 * @throws ParserConfigurationException 
	 */
	public String stepControl(Map<String, String> stepControls,
			DataResourceHandler dRH, String browser, String resolution)
					throws UTAFException, TDMServiceException, XPathParseException, XPathEvalException, NavException{
		String xpath = null;
		String msg = "";

		if(stepControls.get(UtafConstants.XPATH) != null){
			xpath = stepControls.get(UtafConstants.XPATH);
		}else if(StringUtils.isNotEmpty(stepControls.get(UtafConstants.PAGENAME))){
			xpath = xpathBuilder.getElementXpath(stepControls.get(UtafConstants.PAGENAME),
					stepControls.get(UtafConstants.ELEMENTLOCATION), stepControls.get(UtafConstants.ELEMENTTYPE),
					stepControls.get(UtafConstants.ELEMENTNAME));
		}
		
		if (null != stepControls.get(UtafConstants.CONTROL) && stepControls.get(UtafConstants.CONTROL).equals("browser")
				&& stepControls.get(UtafConstants.ACTION).equals("select_url")) {
			String dataInput;
			String startUrl = xmlParser.getEnvValue(stepControls.get("url"));
			if(null != stepControls.get(UtafConstants.DATAINPUT) && StringUtils.isNotEmpty(stepControls.get(UtafConstants.DATAINPUT))){
				try {
					dataInput = getDataInput(stepControls);
					if (startUrl.contains("~var~")) {
						startUrl = startUrl.replace("~var~", dataInput);
					}
				} catch (UTAFException e) {
					msg = stepControls.get(UtafConstants.CONTROL) + " " + stepControls.get(UtafConstants.ACTION) + "FAIL DataResourceException: " + e.getMessage() ;
				}
			}
			seleniumCtrls = new SeleniumCtrls(
					SelectBrowserEnum.valueOf(browser.toUpperCase()),
					startUrl, resolution, waitTime);
			stepValidator = new StepValidator(stepControls,
					seleniumCtrls);
			runStep = new RunStep(seleniumCtrls, waitTime, drv);
		} else {
			if (StringUtils.isNotEmpty(stepControls.get(UtafConstants.CONTROL))) {
				msg = StepRunnerControlsEnum.valueOf(
						stepControls.get(UtafConstants.CONTROL).toUpperCase()).execute(
								stepControls, drv.getPropValue("dataResourcesPath"),
								drv.getPropValue("dataResourceXpath"), dRH);
				if (StringUtils.isNotBlank(msg) && msg.contains("Starting Function:")) {
					functionList.add(msg.replace("Starting Function: ", ""));
				}
			} else {
				if (ActionControlsEnum.enumContains(stepControls.get(UtafConstants.ACTION).toUpperCase())) {
					msg = ActionControlsEnum.valueOf(
							stepControls.get(UtafConstants.ACTION).toUpperCase()).execute(
									stepControls, stepValidator,xpath);
				} else {
					String dataInput = stepControls.get(UtafConstants.DATAINPUT);
					if (dataInput != null && dataInput.contains("Data")) {
						dataInput = dRH.getDataItem(
								dataInput.replace("Data", ""),
								stepControls.get(UtafConstants.DATAKEY));
					}else if(StringUtils.isNotEmpty(dataInput) && dataInput.contains("xml")){
						String fileData =  XMLIncludeBuilder.buildIncludeXMLFromFile(dataInput);
						dataInput = xmlParser.parseData(fileData,drv.getPropValue("dataResourceXpath"));
					}
					msg = runStep.run(stepControls, dataInput,xpath);
					if (stepControls.get(UtafConstants.EXPECTEDRESULTS) != null) {
						if (stepControls.get(UtafConstants.EXPECTEDRESULTS).contains(
								UtafConstants.DATAINPUT)) {
							stepControls.put("action", "validate");
						} else { 
							dataInput = stepControls.get(UtafConstants.DATAINPUT);
						}
					}
				}
			}
		}
		if (msg != null && msg.contains("Stopping Function:")) {
			if (msg.contains(functionList.get(0))) {
				dRH.destroy();
				functionList.clear();
			}
		}
		return msg;
	}

	/**
	 * Get data input
	 * @param step
	 * @return String
	 * @throws XPathParseException
	 * @throws XPathEvalException
	 * @throws NavException
	 * @throws UTAFException
	 * @throws IOException
	 */
	private String getDataInput(Map<String, String> step) throws UTAFException, XPathParseException, XPathEvalException, NavException {
		String dataTypeOption = "";
		if (step.get("data_type").toUpperCase().equals("TDM")) {
			dataTypeOption = env;
		} else {
			dataTypeOption =drv.getPropValue("dataResourceXpath");
		}
		Map<String, String> data = DataTypeSelectorEnum.valueOf(
				step.get(UtafConstants.DATATYPE).toUpperCase()).execute(step.get(UtafConstants.DATAINPUT),
						dataTypeOption);
		return data.get(step.get(UtafConstants.DATAKEY));

	}
}
