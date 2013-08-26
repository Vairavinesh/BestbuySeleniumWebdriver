package com.bestbuy.utaf.enumutil;

import java.io.File;
import java.util.Map;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import com.bestbuy.utaf.exceptions.UTAFException;
import com.bestbuy.utaf.testcaserunner.SeleniumCtrls;
import com.bestbuy.utaf.util.UtafConstants;
import com.bestbuy.utaf.xpathbuilder.XpathBuilder;

/**
 * @author a948621
 * 
 */
public enum ExpectedResultsSelectorEnum {
	URL() {
	
		public String execute(SeleniumCtrls pgObj, Map<String, String> step,String dataInput, XpathBuilder xpathBuilder) {
			String actualUrl = pgObj.getCurrentUrl();
			String expectedUrl = step.get(UtafConstants.EXPECTEDRESULTSURL);
			String msg = "";
			File scrFile = null;
			if (actualUrl.equals(expectedUrl)) {
				msg = urlCorrectMsg(actualUrl.equals(expectedUrl), actualUrl,
						expectedUrl, "", step);
			} else {
				scrFile = ((TakesScreenshot) pgObj.getDriver())
						.getScreenshotAs(OutputType.FILE);
				msg = urlCorrectMsg(actualUrl.equals(expectedUrl), actualUrl,
						expectedUrl, scrFile.getAbsolutePath(), step);
			}
			return msg;
		};
	},
	DATA() {
		
		public String execute(SeleniumCtrls pgObj, Map<String, String> step,String dataInput, XpathBuilder xpathBuilder) throws UTAFException {
			return validateTextInput(pgObj, step, dataInput, xpathBuilder);
		};
	},
	USER_DEFINED() {
	
		public String execute(SeleniumCtrls pgObj, Map<String, String> step,String dataInput, XpathBuilder xpathBuilder) throws UTAFException {
			return validateTextInput(pgObj, step, step.get(UtafConstants.EXPECTEDRESULTSUSERDEFINED), xpathBuilder);

		};
	},
	ELEMENT() {
		public String execute(SeleniumCtrls pgObj, Map<String, String> step,String dataInput, XpathBuilder xpathBuilder) throws UTAFException {
			String msg = "";
			if (step.get(UtafConstants.ELEMENTSTATE).equals("visible")) {
				String xpath = "";
					xpath = xpathBuilder.getElementXpath(step.get(UtafConstants.CONTROL),
							step.get(UtafConstants.EXPECTEDRESULTSELEMENTLOCATION),
							step.get(UtafConstants.EXPECTEDRESULTSELEMENTTYPE),
							step.get(UtafConstants.EXPECTEDRESULTSELEMENTNAME));
				boolean test = SeleniumCtrls.isElementVisible(pgObj.getDriver(), xpath);
				msg = visibleMsg("Link", step, test);
				
			} else if (step.get(UtafConstants.ELEMENTSTATE).equals("checked")) {
				msg = "";
			} else if (step.get(UtafConstants.ELEMENTSTATE).equals("selected")) {
				msg = "";
			}
			return msg;
		};
	};
	public String execute(SeleniumCtrls pgObj, Map<String, String> step,String dataInput, XpathBuilder xpathBuilder) throws UTAFException {
		return null;
	}
	

	public String validateTextInput(SeleniumCtrls pgObj, Map<String, String> step,String dataInput, XpathBuilder xpathBuilder) throws UTAFException{
		String msg = "";
		String actual = "";
		File scrFile = null;
			String xpath = xpathBuilder.getElementXpath(step.get(UtafConstants.CONTROL),
					step.get(UtafConstants.EXPECTEDRESULTSELEMENTLOCATION),
					step.get(UtafConstants.EXPECTEDRESULTSELEMENTTYPE),
					step.get(UtafConstants.EXPECTEDRESULTSELEMENTNAME));
			actual = SeleniumCtrls.getTextboxValue(pgObj.getDriver(), xpath);
			if (!actual.equals(dataInput)) {
				scrFile	 = ((TakesScreenshot) pgObj.getDriver())
						.getScreenshotAs(OutputType.FILE);
				actual = "Screenshot: " + scrFile.getAbsolutePath();
			}
			msg = textCorrectMsg(step, actual, actual.equals(dataInput));
			
		return msg;
	}
	private static String urlCorrectMsg(boolean test, String actualUrl,
			String expectedUrl, String screenShot, Map<String, String> stepControls) {
		String msg = "";
		if (test) {
			msg = "Step " + stepControls.get(UtafConstants.NUMBER) + ","
					+ stepControls.get(UtafConstants.CONTROL) + ","
					+ stepControls.get(UtafConstants.ELEMENTNAME) + "_"
					+ stepControls.get(UtafConstants.ELEMENTTYPE)
					+ ", actual URL matches expected, PASS";
		} else {
			msg = "Step " + stepControls.get(UtafConstants.NUMBER) + ","
					+ stepControls.get(UtafConstants.CONTROL) + ","
					+ stepControls.get(UtafConstants.ELEMENTNAME) + "_"
					+ stepControls.get(UtafConstants.ELEMENTTYPE) + ", actual URL: "
					+ actualUrl + " does not match expected URL: "
					+ expectedUrl + ". Screenshot: " + screenShot + ", FAIL";
		}
		return msg;
	}

	private static String textCorrectMsg(Map<String, String> step,
			String actualValue, boolean test) {
		String msg = "";
		if (test) {
			msg = "Step " + step.get(UtafConstants.NUMBER) + " " + step.get(UtafConstants.ELEMENTNAME) +  " " + step.get(UtafConstants.ELEMENTTYPE) + ", Label is correct, PASS";
		} else {
			msg = "Step " + step.get(UtafConstants.NUMBER) + " " + step.get(UtafConstants.ELEMENTNAME) +  " " + step.get(UtafConstants.ELEMENTTYPE) + ", Label " + actualValue
					+ " does not match expected " + step.get(UtafConstants.ELEMENTNAME) + ", FAIL";
		}
		return msg;
	}

	private static String visibleMsg(String elementType, Map<String, String> step, boolean test) {
		String msg = "";
		if (test) {
			msg = "Step " + step.get(UtafConstants.NUMBER) + " " + step.get(UtafConstants.ELEMENTNAME) +  " " + step.get(UtafConstants.ELEMENTTYPE) + ", is visible, PASS";
		} else {
			msg = "Step " + step.get(UtafConstants.NUMBER) + " " + step.get(UtafConstants.ELEMENTNAME) +  " " + step.get(UtafConstants.ELEMENTTYPE) + ", is not visible, FAIL";
		}
		return msg;
	}
}

