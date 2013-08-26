/**
 * 
 */
package com.bestbuy.utaf.xmlparser;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerFactory;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.StringEscapeUtils;
import org.w3c.dom.*;
import org.xml.sax.SAXException;

import com.bestbuy.utaf.util.LocalFileIO;
import com.ximpleware.NavException;
import com.bestbuy.utaf.enumutil.PageSourceMetaDataXpathSelectorEnum;

/**
 * @author a948621
 * 
 */
public class PageSourceDomParser {

	public void getElementMetaData(String app, String inputFile, String xpath,
			String idString, String pageId, String outputPath,
			String elementType) throws ParserConfigurationException,
			SAXException, IOException, XPathExpressionException, NavException,
			TransformerException {

		Document document;
		DocumentBuilder documentBuilder;
		DocumentBuilderFactory documentBuilderFactory;
		NodeList nodeList;
		// List<String> idsList = new ArrayList<String>();
		// HashMap<String, String> idsList = new HashMap<String, String>();
		// HashMap<String, HashMap<String, String>> attributes = new
		// HashMap<String, HashMap<String, String>>();
		HashMap<String, HashMap<String, String>> elementHashMap = null;
		File xmlInputFile;
		xmlInputFile = new File(inputFile);
		documentBuilderFactory = DocumentBuilderFactory.newInstance();
		documentBuilder = documentBuilderFactory.newDocumentBuilder();
		document = documentBuilder.parse(xmlInputFile);
		javax.xml.xpath.XPath xPath = XPathFactory.newInstance().newXPath();
		nodeList = (NodeList) xPath.evaluate(xpath,
				document.getDocumentElement(), XPathConstants.NODESET);
		elementHashMap = new HashMap<String, HashMap<String, String>>();
		for (int index = 0; index < nodeList.getLength(); index++) {
			Node node = nodeList.item(index);
			if (node.getNodeType() == Node.ELEMENT_NODE) {
				if (node instanceof Element && node.hasAttributes()) {
					if (elementType.equals("link")) {
						HashMap<String, HashMap<String, String>> test = new HashMap<String, HashMap<String, String>>();
						test.putAll(getLinkText(node, idString));
						if (!test.isEmpty()) {
							for (String e : test.keySet()) {

								elementHashMap.put(e, test.get(e));
							}
						}
					}

					// } else if (elementType.equals("button") &&
					// (this.xmlNav.toString(j).contains("type") &&
					// this.xmlNav.toString(j + 1).matches(idString))) {
					//
					// text = getButtonLabel();
					// } else if (elementType.equals("checkbox")) {
					//
					// } else if (elementType.equals("textbox")) {
					//
					// } else if (elementType.equals("radio button") &&
					// (this.xmlNav.toElement(VTDNav.FIRST_CHILD) &&
					// this.xmlNav.toString(j + 1).matches(idString))) {
					// text = getRadioBtnLabel();
					// } else {
					//
					// }
				}
			}
		}
		outputMetaData(app, pageId, elementType, elementHashMap,
				outputPath);
	}

	private void outputMetaData(String app, String pageId, String type,
			HashMap<String, HashMap<String, String>> elements, String outputPath)
			throws ParserConfigurationException, TransformerException,
			SAXException, IOException, XPathExpressionException {
		File f1 = new File(outputPath + File.separatorChar + app + ".xml");
		DocumentBuilderFactory docFactory = DocumentBuilderFactory
				.newInstance();
		DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
		Document doc;
		if (f1.exists()) {
			doc = docBuilder.parse(f1.getAbsolutePath());
			doc.getDocumentElement().normalize();

			updatePages(doc, pageId, app);
//			updateElementTypes(doc, pageId, type);
//			updatePageElements(doc, pageId, type, elements);
			writeNewApplicationXML(doc, f1, "File updated successfully!");
		} else {
			doc = docBuilder.newDocument();
			doc = buildNewApplicationXML(doc, outputPath, type, app, pageId,
					elements);
			writeNewApplicationXML(doc, f1, "File saved!");
		}
	}

	private Document updatePages(Document doc, String pageId, String app) throws XPathExpressionException {
		NodeList nodeList = getNodeListByXpath(doc,
				(PageSourceMetaDataXpathSelectorEnum.valueOf("ITEM").getXpath(
						pageId, pageId, "PAGE")));
		if (nodeList.getLength() > 0) {
			Element pageElement = getElementByXpath(doc,
					PageSourceMetaDataXpathSelectorEnum.valueOf("PAGE")
							.getXpath(pageId, pageId, null));
			doc = insertItem(doc, pageElement, (Element) nodeList.item(0),
					pageId, pageId);
		} else {
			if (null == getElementByXpath(doc,
					PageSourceMetaDataXpathSelectorEnum.valueOf("PAGE")
							.getXpath(pageId, pageId, null))) {
				Element parentElement = getElementByXpath(
						doc,
						PageSourceMetaDataXpathSelectorEnum.valueOf(
								"APPLICATION").getXpath(null, null, null));
				doc = appendPageListParentNode(doc, parentElement, app, pageId);
			}
			Element pageElement = getElementByXpath(doc,
					PageSourceMetaDataXpathSelectorEnum.valueOf("PAGE")
							.getXpath(pageId, pageId, null));
			doc = insertItem(doc, pageElement, (Element) nodeList.item(0),
					pageId, pageId);
		}
		return doc;
	}

//	private Document insertNewPageItem(Document doc, NodeList nodeList,
//			String pageId, String pageXpath, File f1)
//			throws XPathExpressionException {
//
//		Element pageElement = null;
//		pageElement = getElementByXpath(doc, pageXpath);
//		doc = insertItem(doc, pageElement, (Element) nodeList.item(0), pageId,
//				pageId);
//		return doc;
//	}

//	private static void updateElementValue(Document doc, String nodeName,
//			String value) {
//		NodeList employees = doc.getElementsByTagName(nodeName);
//		Element emp = null;
//		// loop for each employee
//		for (int i = 0; i < employees.getLength(); i++) {
//			emp = (Element) employees.item(i);
//			Node name = emp.getElementsByTagName("item").item(0)
//					.getFirstChild();
//			name.setNodeValue(name.getNodeValue().toUpperCase());
//		}
//	}

	private Document insertItem(Document doc, Element parentElement,
			Element existingItem, String attribValue, String nodeValue) {
		Element item = doc.createElement("item");
		doc = appendAtribute(doc, item, "value", attribValue);
		item.appendChild(doc.createTextNode(nodeValue));
		parentElement.insertBefore(item, existingItem);
		return doc;
	}

	private NodeList getNodeListByXpath(Document doc, String xpath)
			throws XPathExpressionException {
		javax.xml.xpath.XPath xPath = XPathFactory.newInstance().newXPath();
		NodeList nodeList = null;
		try {
			nodeList = (NodeList) xPath.evaluate(xpath,
					doc.getDocumentElement(), XPathConstants.NODESET);
		} catch (NullPointerException n) {
			nodeList = null;
		}
		return nodeList;
	}

	private Element getElementByXpath(Document doc, String xpath)
			throws XPathExpressionException {

		javax.xml.xpath.XPath xPath = XPathFactory.newInstance().newXPath();
		NodeList nodeList = (NodeList) xPath.evaluate(xpath,
				doc.getDocumentElement(), XPathConstants.NODESET);
		System.out.println(nodeList.getLength());
		return (Element) nodeList.item(0);
	}


	private boolean elementIsPresent(NodeList nodeList, String attribName,
			String attribValue) {
		boolean present = false;
		for (int index = 0; index < nodeList.getLength(); index++) {
			Node node = nodeList.item(index);
			if (getAttributeValue(node, attribName).equals(attribValue)) {
				present = true;
				break;
			}
		}
		return present;
	}

	private Document appendPageListParentNode(Document doc,
			Element application, String app, String pageId) {
		Element pageList = doc.createElement("list");
		doc = appendAtribute(doc, pageList, "id", "type");
		if (pageId.contains("lightbox")) {
			doc = appendAtribute(doc, pageList, "type", pageId);
		}
		doc = appendAtribute(doc, pageList, "id", "page");
		doc = appendAtribute(doc, pageList, "application", app);
		addItem(doc,pageList, pageId, pageId);
		application.appendChild(pageList);
		return doc;
	}

	private Document appendTypeParentNode(Document doc, Element application,
			String app, String pageId, String type) {
		Element elementTypeList = doc.createElement("list");
		doc = appendAtribute(doc, elementTypeList, "application", app);
		doc = appendAtribute(doc, elementTypeList, "page", pageId);
		doc = appendAtribute(doc, elementTypeList, "id", "type");
		doc = addItem(doc, elementTypeList, type, type);
		application.appendChild(elementTypeList);
		return doc;
	}

	private Document buildNewApplicationXML(Document doc, String outputPath,
			String type, String app, String pageId,
			HashMap<String, HashMap<String, String>> elements)
			throws TransformerException {
		// create root node for application
		Element application = doc.createElement("application");
		doc = appendAtribute(doc, application, "id", app);
		doc.appendChild(application);

		// create list for application pages
		appendPageListParentNode(doc, application, pageId, pageId);
		
		// create element types metadata
		appendTypeParentNode(doc, application, app, pageId, type);
		// create list for element type metadata
		Element list = doc.createElement("list");
		doc = appendAtribute(doc, list, "application", app);
		doc = appendAtribute(doc, list, "page", pageId);
		doc = appendAtribute(doc, list, "type", type);
		doc = appendAtribute(doc, list, "id", type + "s");
		for (String e : elements.keySet()) {
			System.out.println(e);
			HashMap<String, String> attribsHashMap = elements.get(e);
			if (attribsHashMap.containsKey("id")) {
				doc = addItem(doc, list, attribsHashMap.get("id"), e);
			} else if (attribsHashMap.containsKey("class")
					&& !attribsHashMap.get("class").contains("footer")  ) {
				doc = addItem(doc, list, attribsHashMap.get("class"), e);
			} else {
				doc = addItem(doc, list, e, e);
			}
		}
		application.appendChild(list);
		// write the content into xml file
		return doc;
	}

	private Document appendAtribute(Document doc, Element element,
			String attributeId, String value) {
		Attr temp = doc.createAttribute(attributeId);
		temp.setValue(value);
		element.setAttributeNode(temp);
		return doc;
	}

	private Document addItem(Document doc, Element parentElement,
			String attribValue, String nodeValue) {
		Element item = doc.createElement("item");
		doc = appendAtribute(doc, item, "value", attribValue);
		item.appendChild(doc.createTextNode(nodeValue));
		parentElement.appendChild(item);
		return doc;
	}

	private void writeNewApplicationXML(Document doc, File file, String msg)
			throws TransformerException {
		LocalFileIO io = new LocalFileIO();
		String path = file.getPath().replace(
				File.separatorChar + file.getName(), "");
		io.crtFolder(path);
		TransformerFactory transformerFactory = TransformerFactory
				.newInstance();
		Transformer transformer = transformerFactory.newTransformer();
		DOMSource source = new DOMSource(doc);
		StreamResult result = new StreamResult(new File(file.getAbsolutePath()));
		transformer.transform(source, result);
		System.out.println(msg);
	}

	private boolean hasAttribute(Node node, String attribute) {
		boolean has = false;
		org.w3c.dom.NamedNodeMap attrs = node.getAttributes();
		for (int i = 0; i < attrs.getLength(); i++) {
			Attr attrib = (Attr) attrs.item(i);
			if (attribute.equals(attrib.getName())) {
				has = true;
				break;
			}
		}
		return has;
	}

	private String getAttributeValue(Node node, String attribute) {
		String value = "";
		org.w3c.dom.NamedNodeMap attrs = node.getAttributes();
		for (int i = 0; i < attrs.getLength(); i++) {
			Attr attrib = (Attr) attrs.item(i);
			if (attribute.equals(attrib.getName())) {
				value = StringUtils.trim(attrib.getValue());
				break;
			}
		}
		return value;
	}

	private HashMap<String, String> getAttributes(Node node) {
		HashMap<String, String> attribsHashMap = new HashMap<String, String>();
		org.w3c.dom.NamedNodeMap attrs = node.getAttributes();
		for (int i = 0; i < attrs.getLength(); i++) {
			Attr attrib = (Attr) attrs.item(i);
			attribsHashMap.put(attrib.getName(), attrib.getValue());
		}
		return attribsHashMap;
	}

	private HashMap<String, HashMap<String, String>> getLinkText(Node node,
			String idString) throws NavException, UnsupportedEncodingException,
			DOMException {
		HashMap<String, String> attributes = new HashMap<String, String>();
		HashMap<String, HashMap<String, String>> element = new HashMap<String, HashMap<String, String>>();
		String text = "";
		String id = "";
		if (hasAttribute(node, "class")
				&& getAttributeValue(node, "class").contains("content")) {
			text = null;
		} else if (hasAttribute(node, "href")
				|| node.getNodeName().matches(idString)) {
			text = StringEscapeUtils.unescapeXml(StringUtils.trim(node
					.getTextContent().replaceAll("\\s{2,}", " ")));
			if (StringUtils.isNotEmpty(text)) {
				attributes.putAll(getAttributes(node));
				element.put(text, attributes);
			}

			if (StringUtils.isEmpty(text) && hasAttribute(node, "id")) {
				text = StringUtils.capitalize(getAttributeValue(node, "id"));
				if (text.contains("_")) {
					text = getAttributeValue(node, "alt");
				}
				if (StringUtils.isNotEmpty(text)) {
					attributes.putAll(getAttributes(node));
					element.put(text, attributes);
				}
			}
		}
		return element;
	}
}
