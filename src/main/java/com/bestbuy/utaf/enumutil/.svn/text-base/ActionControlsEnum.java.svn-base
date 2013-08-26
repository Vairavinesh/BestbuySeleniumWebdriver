package com.bestbuy.utaf.enumutil;

import java.util.Map;

import com.bestbuy.utaf.exceptions.UTAFException;
import com.bestbuy.utaf.testcaserunner.StepValidator;
import com.bestbuy.utaf.util.UtafConstants;

/**
 * @author a948621
 *
 */
public enum ActionControlsEnum {
	VISIBLE() {
		public String execute(Map<String, String> stepControls, StepValidator stepValidator, String xpath) throws UTAFException {
			return stepValidator.visible(xpath);

		};
	},
	VALIDATE_URL() {
		public String execute(Map<String, String> stepControls, StepValidator stepValidator, String xpath) {
			String dataInput = stepControls.get(UtafConstants.URL);
			String msg = stepValidator.validateUrl(dataInput);
			return msg;
		};
	},
	VALIDATE_LABEL() {
		public String execute(Map<String, String> stepControls, StepValidator stepValidator, String xpath) throws UTAFException {
			String dataInput = stepControls.get(UtafConstants.TEXT);
			String msg = stepValidator.validateLabel(dataInput, xpath);
			return msg;
		};
	},
	VALIDATE_TEXT() {
		public String execute(Map<String, String> stepControls, StepValidator stepValidator, String xpath) throws UTAFException {
			String dataInput = "";
			String msg = "";
			if (stepControls.get(UtafConstants.TEXT).contains(UtafConstants.DATAINPUT)) {
				stepValidator.validateText(stepControls, dataInput,xpath);
			} else {
				dataInput = stepControls.get(UtafConstants.TEXT);
				msg = stepValidator.validateText(stepControls, dataInput,xpath);
			}
			return msg;
		};
	};

	/**
	 * Check whether the enum value in ActionControlsEnum
	 * @param enumValue
	 * @return boolean
	 */
	public static boolean enumContains(String enumValue){
		try{
			ActionControlsEnum.valueOf(enumValue);
			return true;
		}catch(Exception e){
			return false;
		}
	}
	public String execute(Map<String, String> stepControls, StepValidator stepValidator, String xpath) throws UTAFException {
		return null;
	}

}
