package com.bestbuy.utaf.xmlparser;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.log4j.Logger;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.bestbuy.utaf.exceptions.UTAFException;

public class XpathXMLParser {
	private static Logger log = Logger.getLogger("XpathXMLParser");

	private Map<String, String> xMap;
	private String file = "";

	/**
	 * Default Constructor
	 * @param file
	 */
	public XpathXMLParser(String file) {
		xMap = new HashMap<String, String>();
		this.file = file;
	}

	/**
	 * Get
	 * @param pageId
	 * @param elementType
	 * @return String
	 * @throws IOException 
	 * @throws SAXException 
	 * @throws ParserConfigurationException 
	 */
	public String getXpath(String pageId, String elementType) throws UTAFException {
		log.info(file);
		String nextStep = "";
		boolean test = getElementIds(pageId, elementType);
		if (test) {
			nextStep = getXpathString(pageId, elementType);

		} else if (getElementIds("common", elementType) & !test) {
			nextStep = getXpathString("common", elementType);
		} else {
			nextStep = "";
		}
		return nextStep;
	}

	/**
	 * Get
	 * @param pageId
	 * @param elementType
	 * @param attribId
	 * @return String
	 * @throws IOException 
	 * @throws SAXException 
	 * @throws ParserConfigurationException 
	 */
	public String getXpathAttrib(String pageId, String elementType, String attribId) throws UTAFException {
		log.info(file);
		String nextStep = "";
		boolean test = getElementIds(pageId, elementType);
		if (test) {
			nextStep = getXpathAttribValue(pageId, elementType, attribId);

		} else if (getElementIds("common", elementType) & !test) {
			nextStep = getXpathAttribValue("common", elementType, attribId);
		} else {
			nextStep = "";
		}
		return nextStep;
	}

	/**
	 * Get text content
	 * @param pageId
	 * @param elementType
	 * @return String
	 * @throws IOException 
	 * @throws SAXException 
	 * @throws ParserConfigurationException 
	 */
	private String getXpathString(String pageId, String elementType) throws UTAFException {
		String text = "";
		Element elementXpathString = getElement(pageId, elementType);
		if(elementXpathString != null){
			xMap.put(elementXpathString.getAttribute("id"), elementXpathString.getTextContent());
			text = elementXpathString.getTextContent();
		}
		return text;
	}


	/**
	 * Get attribute value for the given attribute Id
	 * @param pageId
	 * @param elementType
	 * @param attribId
	 * @return String
	 * @throws UTAFException 
	 * @throws IOException 
	 * @throws SAXException 
	 * @throws ParserConfigurationException 
	 */
	public String getXpathAttribValue(String pageId,String elementType, String attribId) throws UTAFException {
		String text = "";
		Element elementValue = getElement(pageId, elementType);
		if(elementValue != null){
			text = getAttributeValue(elementValue, attribId);
			xMap.put(elementValue.getAttribute("id"), elementValue.getTextContent());
		}
		return text;
	}

	/**
	 * Get Attribute value
	 * @param node
	 * @param attribId
	 * @return String
	 */
	private String getAttributeValue(Node node, String attribId) {
		String found = "";
		NamedNodeMap attribs = node.getAttributes();
		int length = attribs.getLength();
		log.info("NODE attribs len = " + length);
		for (int i = 0; i < length; i++) {
			attribs.item(i);
			for (int j = 0; j < length; j++) {
				Attr attrGet = (Attr) attribs.item(j);
				log.info(attrGet.getName() + "    " + attrGet.getValue());
				if (attrGet.getName().equals(attribId)) {
					found = attrGet.getValue();
					break;
				}
			}
		}
		return found;
	}

	/**
	 * Check whether element is available or not
	 * @param pageId
	 * @param elementType
	 * @return boolean
	 * @throws UTAFException 
	 * @throws IOException 
	 * @throws SAXException 
	 * @throws ParserConfigurationException 
	 */
	private boolean getElementIds(String pageId, String elementType) throws UTAFException{
		boolean found = false;
		Element element;
		element = getElement(pageId, elementType);
		if(element != null){
			found = true;
			xMap.put(element.getAttribute("id"),element.getTextContent());
		}
		return found;
	}

	/**
	 * Get Element
	 * @param pageId
	 * @param elementType
	 * @return Element
	 * @throws ParserConfigurationException 
	 * @throws SAXException 
	 * @throws IOException 
	 * @throws UTAFException 
	 */
	private Element getElement(String pageId, String elementType) throws  UTAFException{
		Document document;
		DocumentBuilder documentBuilder;
		DocumentBuilderFactory documentBuilderFactory;
		NodeList nodeList = null;
		Element elementData = null;
		try {
			InputStream is = this.getClass().getClassLoader().getResourceAsStream(file);
			documentBuilderFactory = DocumentBuilderFactory.newInstance();
			documentBuilder = documentBuilderFactory.newDocumentBuilder();
			document = documentBuilder.parse(is);
			nodeList = document.getElementsByTagName("page");
			document.getDocumentElement().normalize();
			for (int index = 0; index < nodeList.getLength(); index++) {
				Node node = nodeList.item(index);
				if (node.getNodeType() == Node.ELEMENT_NODE) {
					Element element = (Element) node;
					if (pageId.toLowerCase().equals(element.getAttribute("name"))) {
						NodeList eList = node.getChildNodes();
						for (int index2 = 0; index2 < eList.getLength(); index2++) {
							Node eNode = eList.item(index2);
							if (eNode.getNodeType() == Node.ELEMENT_NODE) {
								Element e = (Element) eNode;
								if (elementType.toLowerCase().equals(e.getAttribute("id"))) {
									elementData = e;
								}
							}
						}
					}
				}
			}
		} catch (ParserConfigurationException parseExcep) {
			log.error("ParserConfiguration Exception occured getElement method in XpathXMLParser : "+parseExcep.getMessage());
			throw new UTAFException("ParserConfigurationException Occurred"+parseExcep.getMessage());
		} catch (SAXException saxExcep) {
			log.error("SAXException Exception occured getElement method in XpathXMLParser : "+saxExcep.getMessage());
			throw new UTAFException("SAXException Occurred"+saxExcep.getMessage());
		} catch (IOException ioExcep) {
			log.error("IOException Exception occured getElement method in XpathXMLParser : "+ioExcep.getMessage());
			throw new UTAFException("IOException Occurred"+ioExcep.getMessage());
		}
		return elementData;
	}

}
