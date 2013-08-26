package com.bestbuy.utaf.enumutil;

import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.internal.seleniumemulation.DragAndDrop;

import com.bestbuy.utaf.exceptions.UTAFException;
import com.bestbuy.utaf.xmlparser.VTDXMLParser;
import com.ximpleware.NavException;
import com.ximpleware.XPathEvalException;
import com.ximpleware.XPathParseException;
import com.bestbuy.fta.basedataframework.data.*;
/**
 * @author a948621
 * 
 */
public enum DataTypeSelectorEnum {
	HARDCODED() {
		public Map<String, String> execute(String file, String xpath) throws UTAFException, XPathParseException, XPathEvalException, NavException {
			VTDXMLParser dR;
			try {
				dR = new VTDXMLParser(file);
			} catch (FileNotFoundException e) {
				throw new UTAFException("Hardcoded: " + file + " not found. " + e.getMessage());
			
			}
			return dR.getAllTextNodes(xpath);
		};
	},
	TDM() {
		public Map<String, String> execute(String tdmIdentifier, String environement) throws UTAFException {
			DataFactory tdm = new DataFactory();
			Map<String, String> temp = new HashMap<String, String>();
			Map<String, Object> tdmMap = tdm.getDataByID(tdmIdentifier, environement);
			for (String keyString : tdmMap.keySet()) {
				temp.put(keyString, (String) tdmMap.get(keyString));
			}
			return temp;
		};
	};

	public Map<String, String> execute(
			String string1, String string2) throws UTAFException, XPathParseException, XPathEvalException, NavException  {
		return null;
	}
}
