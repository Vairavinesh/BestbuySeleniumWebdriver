package com.bestbuy.utaf.pagesource;

import java.util.HashMap;
import java.util.Map;

import com.ximpleware.AutoPilot;
import com.ximpleware.VTDGen;
import com.ximpleware.VTDNav;

public class PageSourceparser {
	private VTDNav vn;
	private static String xmlLocation = "src/main/resources/Environment_Configuration/PageSource.xml";
	private static String pagesourceXpath = "//*/url";
	private static Map<String, String> pageSourceUrl = new HashMap<String, String>();
	private static Map<String, String> pageSourceType = new HashMap<String, String>();
	private String urlName = "";
	private String pagesourceType = "";

	public void executePageSourcexml() {

		VTDGen vg = new VTDGen();
		int i;
		if (vg.parseFile(xmlLocation, true)) {
			try {
				vn = vg.getNav();
				AutoPilot ap = new AutoPilot(vn);
				ap.selectXPath(pagesourceXpath);
				while ((i = ap.evalXPath()) != -1) {
					int attrcount = vn.getAttrCount() * 2;
					for (int j = 1; j <= attrcount; j++) {
						if (vn.toString(i + j).equals("name")) {
							int attr = j + 1;
							urlName = vn.toString(i + attr);
						}
						if (vn.toString(i + j).equals("type")) {
							int attr = j + 1;
							pagesourceType = vn.toString(i + attr);
						}
					}
					getPageSourceUrl().put(urlName,
							vn.toString(i + attrcount + 1));
					getPageSourceType().put(urlName, pagesourceType);
				}
			} catch (Exception e) {
			}
		}
	}

	/**
	 * @return the pageSourceUrl
	 */
	public static Map<String, String> getPageSourceUrl() {
		return pageSourceUrl;
	}

	/**
	 * @return the pageSourceType
	 */
	public static Map<String, String> getPageSourceType() {
		return pageSourceType;
	}
}
