package com.bestbuy.utaf.xpathbuilder;

import java.io.IOException;
import java.util.logging.Logger;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import com.bestbuy.utaf.exceptions.UTAFException;
import com.bestbuy.utaf.xmlparser.XpathXMLParser;

public class XpathBuilder {

	private XpathXMLParser xpathDefinitions;
	
	private static Logger log = Logger.getLogger("XpathBuilder");

	/**
	 * Constructor
	 * @param xpathFile
	 */
	public XpathBuilder(String xpathFile) {
		xpathDefinitions = new XpathXMLParser(xpathFile);
	}

	/**
	 * Get the xPath for the element
	 * @param pageId
	 * @param elementLocation
	 * @param elementType
	 * @param elementName
	 * @return String
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 * @throws IOException
	 */
	public String getElementXpath(String pageId, String elementLocation,
			String elementType, String elementName) throws UTAFException {
		String xpath = xpathDefinitions.getXpath(pageId, elementType);
		xpath = replaceLocationVar(elementType, elementLocation, pageId, xpath);
		xpath = replaceIdVar(elementName, xpath);
		log.info("XPath : "+xpath); 
		return xpath;

	}

	/**
	 * Replace ~locationVar~ with location
	 * @param elementType
	 * @param elementLocation
	 * @param pageId
	 * @param xpath
	 * @return String
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 * @throws IOException
	 */
	private String replaceLocationVar(String elementType,
			String elementLocation, String pageId, String xpath) throws UTAFException {
		String location = "";
		if (elementLocation != null && xpath.contains("~locationVar~")) {
				location = xpathDefinitions.getXpathAttrib(pageId,
						elementType, elementLocation + "Loc");
			xpath = xpath.replace("~locationVar~", location);
		} else {
			xpath = xpath.replace("~locationVar~//*", "");
		}
		return xpath;
	}

	/**
	 * Replace ~idVar~ with the elementName
	 * @param elementName
	 * @param xpath
	 * @return String
	 */
	private String replaceIdVar(String elementName, String xpath) {
		xpath = xpath.replace("~idVar~", elementName);
		return xpath;
	}


}
