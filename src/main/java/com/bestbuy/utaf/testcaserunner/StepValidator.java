package com.bestbuy.utaf.testcaserunner;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import com.bestbuy.utaf.exceptions.UTAFException;
import com.bestbuy.utaf.util.UtafConstants;

public class StepValidator{

	private Map<String, String> stepControls;
	private SeleniumCtrls seleniumCtrls;

	/** Constructor
	 * @param masterId
	 * @param drv
	 * @param xpathBuilder
	 * @throws IOException 
	 */
	StepValidator(Map<String, String> stepControls, SeleniumCtrls seleniumCtrls){
		this.stepControls = stepControls;
		this.seleniumCtrls = seleniumCtrls;
	}

	public String validateLabel(String dataInput, String xpath) throws UTAFException {
		String msg = "";
		String actual = "";
		File scrFile = null;
		actual = SeleniumCtrls.getElementText(seleniumCtrls.getDriver(), xpath);
		if (!actual.equals(dataInput)) {
			scrFile	 = ((TakesScreenshot) seleniumCtrls.getDriver()).getScreenshotAs(OutputType.FILE);
			actual = "Screenshot: " + scrFile.getAbsolutePath();
		}
		msg = textCorrectMsg(stepControls, actual, actual.equals(dataInput));
		return msg;
	}

	public String validateUrl(String expectedUrl){
		String actualUrl = seleniumCtrls.getCurrentUrl();
		String msg = "";
		File scrFile = null;
		if (actualUrl.equalsIgnoreCase(expectedUrl)) {
			msg = urlCorrectMsg(actualUrl.equals(expectedUrl), actualUrl,
					expectedUrl, "");
		} else {
			scrFile = ((TakesScreenshot) seleniumCtrls.getDriver())
					.getScreenshotAs(OutputType.FILE);
			msg = urlCorrectMsg(actualUrl.equals(expectedUrl), actualUrl,
					expectedUrl, scrFile.getAbsolutePath());
		}
		return msg;
	}

	public String validateText(Map<String, String> stepControls, String dataInput, String xpath) throws UTAFException{
		String msg = "";
		String actual = "";
		File scrFile = null;
		if (stepControls.get(UtafConstants.ELEMENTTYPE).equals("textbox")) {
			actual = SeleniumCtrls.getTextboxValue(seleniumCtrls.getDriver(), xpath);
		} else {
			actual = SeleniumCtrls.getElementText(seleniumCtrls.getDriver(), xpath);
		}
		if (!actual.equals(dataInput)) {
			scrFile	 = ((TakesScreenshot) seleniumCtrls.getDriver())
					.getScreenshotAs(OutputType.FILE);
			actual = "Screenshot: " + scrFile.getAbsolutePath();
		}
		msg = textCorrectMsg(stepControls, actual, actual.equals(dataInput));

		return msg;
	}

	public String visible(String xpath) throws UTAFException {
		boolean test = SeleniumCtrls.isElementVisible(seleniumCtrls.getDriver(), xpath);
		return visibleMsg("Link", stepControls, test);
	}

	private String urlCorrectMsg(boolean test, String actualUrl,
			String expectedUrl, String screenShot) {
		String msg = "";
		if (test) {
			msg = "Step Validate_URL : Actual URL matches Expected, PASS";
		} else {
			msg = "Step Validate_URL : Actual URL: "
					+ actualUrl + " does not match expected URL: "
					+ expectedUrl + ". Screenshot: " + screenShot + ", FAIL";
		}
		return msg;
	}

	private String textCorrectMsg(Map<String, String> step,
			String actualValue, boolean test) {
		String msg = "";
		if (test) {
			msg = "Step "+ step.get(UtafConstants.ELEMENTNAME) +  " " + step.get(UtafConstants.ELEMENTTYPE) + ", " + actualValue + "  is correct, PASS";
		} else {
			msg = "Step "+ step.get(UtafConstants.ELEMENTNAME) +  " " + step.get(UtafConstants.ELEMENTTYPE) + ", " + actualValue
					+ " does not match expected " + stepControls.get(UtafConstants.ELEMENTNAME) + ", FAIL";
		}
		return msg;
	}

	private String visibleMsg(String elementType, Map<String, String> step, boolean test) {
		String msg = "";
		if (test) {
			msg = "Step " + " " + step.get(UtafConstants.ELEMENTNAME) +  " " + step.get(UtafConstants.ELEMENTTYPE) + ", is visible, PASS";
		} else {
			msg = "Step " + step.get(UtafConstants.ELEMENTNAME) +  " " + step.get(UtafConstants.ELEMENTTYPE) + ", is not visible, FAIL";
		}
		return msg;
	}
}
