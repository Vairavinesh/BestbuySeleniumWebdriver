package com.bestbuy.utaf.enumutil;

import java.util.Map;

import com.bestbuy.utaf.exceptions.UTAFException;
import com.bestbuy.utaf.testcaserunner.DataResourceHandler;
import com.bestbuy.utaf.util.UtafConstants;

/**
 * @author a948621
 * 
 */
public enum StepRunnerControlsEnum {
	COMMENT() {
		public String execute(Map<String, String> stepControls, String dataResourceBasePath, String dataResourceXpath, DataResourceHandler dataRsc) {
			return stepControls.get(UtafConstants.COMMENT);
		};
	},
	DATA_INPUT() {
		
		public String execute(Map<String, String> stepControls, String dataResourceBasePath, String dataResourceXpath, DataResourceHandler dataRsc)  {
			String msg ="";
			try {
				msg = dataRsc.buildResources(stepControls, dataResourceBasePath, dataResourceXpath);
			} catch (UTAFException e) {
				msg= stepControls.get(UtafConstants.NUMBER) + "DataResource Build Failure.   FAIL " + e.getMessage();
			}
			return msg;
		};
	};

	/**
	 * @param stepControls
	 * @param propValue
	 * @param propValue2
	 * @param dRH
	 * @return String
	 */
	public String execute(Map<String, String> stepControls,
			String propValue, String propValue2, DataResourceHandler dRH) {
		return null;
	}
}
