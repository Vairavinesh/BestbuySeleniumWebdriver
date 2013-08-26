package com.bestbuy.utaf.testcaserunner;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.xml.sax.SAXException;

import com.bestbuy.utaf.enumutil.ExpectedResultsSelectorEnum;
import com.bestbuy.utaf.exceptions.UTAFException;
import com.bestbuy.utaf.pagesource.GetCurrentDir;
import com.bestbuy.utaf.util.DriverProps;
import com.bestbuy.utaf.util.UtafConstants;
import com.bestbuy.utaf.xpathbuilder.XpathBuilder;

public class RunStep {
	
	private static Logger log = Logger.getLogger("XMLParser");
	
	private SeleniumCtrls seleniumCtrls;
	private int waitTime;
	private XpathBuilder xpathBuilder;
	private DriverProps driverProp;

	/**
	 * Constructor
	 * @param seleniumCtrls
	 * @param waitTime
	 * @param driver
	 * @throws IOException
	 */
	public RunStep(SeleniumCtrls seleniumCtrls, int waitTime, DriverProps driver){
		this.seleniumCtrls = seleniumCtrls;
		this.waitTime = waitTime;
		this.driverProp = driver;
		xpathBuilder = new XpathBuilder(driverProp.getPropValue("xPathDefinitionFilePath"));
	}
	
	/**
	 * Runs the step
	 * @param step
	 * @param dataInput
	 * @return String
	 * @throws IOException 
	 * @throws SAXException 
	 * @throws ParserConfigurationException 
	 */
	public String run(Map<String, String> step, String dataInput, String xpath)
			throws UTAFException {
		String msg = "";
		if (step.get(UtafConstants.ELEMENTTYPE).equals("button") || (step.get(UtafConstants.ELEMENTTYPE).contains("link"))) {
			boolean results = SeleniumCtrls.clickElement(seleniumCtrls.getDriver(), xpath,waitTime);
			msg = clickElement(results, step, dataInput);
		} else if (step.get(UtafConstants.ELEMENTTYPE).contains("textbox")) {
			if (step.get(UtafConstants.ACTION).equals("set")) {
				SeleniumCtrls.setTextBox(seleniumCtrls.getDriver(), xpath, dataInput,
						waitTime);
			} else {
				SeleniumCtrls.clickTextboxButton(seleniumCtrls.getDriver(), xpath, waitTime);
			}
			if (step.get(UtafConstants.EXPECTEDRESULTS) != null) {
				msg = ExpectedResultsSelectorEnum.valueOf(
						step.get(UtafConstants.EXPECTEDRESULTS).toUpperCase()).execute(
						seleniumCtrls, step, dataInput, xpathBuilder);

			}
		} else if (step.get(UtafConstants.ELEMENTTYPE).equals("list")) {
			//TODO : Needs to be rewritten to properly handle list elements for TOM!!!!
			boolean result = SeleniumCtrls.selectText(seleniumCtrls.getDriver(), xpath,
					step.get(UtafConstants.ELEMENTNAME));
			msg = clickElement(result, step, dataInput);
		}
		return msg;
	}
	
	private String clickElement(boolean results, Map<String, String> step, String dataInput) throws UTAFException{
		String msg ="";
		if (results) {
			if (step.get(UtafConstants.EXPECTEDRESULTS) != null) {
				msg = ExpectedResultsSelectorEnum.valueOf(
						step.get(UtafConstants.EXPECTEDRESULTS).toUpperCase())
						.execute(seleniumCtrls, step, dataInput, xpathBuilder);
			}
		} else {
			try {
				File scrFile = ((TakesScreenshot) seleniumCtrls.getDriver())
						.getScreenshotAs(OutputType.FILE);
				msg = objectNotFound(scrFile, step,
						GetCurrentDir.getScreenShotCurrentDirectory());
			} catch (Exception e) {
				log.error("Exception occurred in clickElement : "+e);
				throw new UTAFException("Exception occurred in clickElement method : "+e.getMessage());
			}
		}
		return msg;
	}

	private String objectNotFound(File scrFile, Map<String, String> step,
			String screenShot) {
		String resultMsg = "";
		try {
			resultMsg = "step_" + step.get(UtafConstants.NUMBER) + step.get(UtafConstants.ELEMENTNAME)
					+ step.get(UtafConstants.ELEMENTTYPE);
			FileUtils.copyFile(scrFile, new File(screenShot + "/" + resultMsg
					+ ".png"));
		} catch (IOException e) {
			e.printStackTrace();
		} 
		return resultMsg;
	}
}
