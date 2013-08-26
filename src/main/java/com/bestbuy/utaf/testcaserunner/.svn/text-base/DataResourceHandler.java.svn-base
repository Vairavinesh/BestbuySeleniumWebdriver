/**
 * 
 */
package com.bestbuy.utaf.testcaserunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.bestbuy.fta.basedataframework.xsd.XMLIncludeBuilder;
import com.bestbuy.utaf.enumutil.DataTypeSelectorEnum;
import com.bestbuy.utaf.exceptions.UTAFException;
import com.bestbuy.utaf.util.UtafConstants;

/**
 * @author a948621
 * 
 */
public class DataResourceHandler {

	private List<Map<String, String>> dataResources;

	public DataResourceHandler(){
	}

	public String buildResources(Map<String, String> step,
			String dataResourceBasePath, String dataResourceXpath)
					throws UTAFException{
		if (step.get(UtafConstants.CONTROL).equals(UtafConstants.DATAINPUT)) {
			dataResources = new ArrayList<Map<String, String>>();
			List<String> dataInputs = Arrays.asList(step.get(UtafConstants.DATAINPUTS)
					.split(","));
			List<String> dataTypes = Arrays.asList(step.get(UtafConstants.DATATYPES)
					.split(","));
			int stop = dataInputs.size();
			String file = "";
			for (int i = 0; i < stop; i++) {
				try {
					file = dataInputs.get(i);
					String fileData =  XMLIncludeBuilder.buildIncludeXMLFromFile(file);
					this.dataResources.add(DataTypeSelectorEnum.valueOf(
							dataTypes.get(i).toUpperCase()).execute(fileData,
									dataResourceXpath));
				} catch (Exception e) {
					throw new UTAFException("DataResource Builder Failed"+e.getMessage());
				}

			}
		}
		return "DataResource Built Successfully";
	}

	public String getDataItem(String resourceId, String itemKey)
			throws NullPointerException {
		return ((HashMap<String, String>) this.dataResources.get(Integer
				.parseInt(resourceId.replace("Data", "")))).get(itemKey);
	}

	//	private HashMap<String, String> getDataResource(String resourceId) {
	//		return (HashMap<String, String>) this.dataResources.get(Integer
	//				.parseInt(resourceId.replace("Data", "")));
	//	}

	// private void buildHardCoded(String dataResource, String xpath)
	// throws FileNotFoundException {
	// VTDXMLParser dR = new VTDXMLParser(dataResource);
	// this.dataResources.add(dR.getAllTextNodes(xpath));
	// }

	// private void buildTDM(String tdmQuery, String xpath)
	// throws FileNotFoundException {
	// VTDXMLParser dR = new VTDXMLParser(tdmQuery);
	// this.dataResources.add(dR.getAllTextNodes(xpath));
	// }

	public void destroy() {
		if (this.dataResources.size() > 0) {
			this.dataResources.clear();
		}
		this.dataResources = null;
	}
}
