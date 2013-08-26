package com.bestbuy.utaf.xmlparser;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.bestbuy.fta.basedataframework.common.exception.TDMServiceException;
import com.bestbuy.fta.basedataframework.xsd.XMLIncludeBuilder;
import com.bestbuy.utaf.exceptions.UTAFException;

/**
 * @author Kalaiselvi Jaganathan
 *
 */ 
public class XMLParser {

	private static Logger log = Logger.getLogger("XMLParser");

	private List<Map<String, String>> nodes;
	private Map<String, String> stepsMap;
	private Map<String, String> functionStart;
	private Map<String, String> subFunctionMap;
	private Map<String, String> functionStop;
	private Map<String,String> attributeMap;
	private Map<String, String> envMap;

	public List<Map<String, String>> parseXML(String xmlPath) throws UTAFException{
		DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder;
		try {
			builder = builderFactory.newDocumentBuilder();
			this.nodes = new ArrayList<Map<String, String>>();
			nodes = this.recursiveCall(builder, xmlPath,nodes);
		} catch (ParserConfigurationException e) {
			throw new UTAFException("ParserConfigurationException occured during XML Parsing :"+e.getMessage());
		} catch (UTAFException e) {
			log.error("UTAFException occured during XML Parsing : "+e.getMessage());
		}
		return nodes;
	}

	private List<Map<String, String>>  recursiveCall(DocumentBuilder builder, String xmlPath,List<Map<String, String>> nodes) throws UTAFException {
		try{
			this.stepsMap = new HashMap<String, String>();
			Document document = this.getRootNode(builder, xmlPath);
			XPath xPath =  XPathFactory.newInstance().newXPath();
			String expression = "//step";
			NodeList nodeList = (NodeList) xPath.compile(expression).evaluate(document, XPathConstants.NODESET);
			for(int i=0; i<nodeList.getLength();i++){
				if(nodeList.item(i)  == null){
					continue;
				}
				String actionAttribute = this.getAttribute(nodeList.item(i), "action");
				if (StringUtils.isNotBlank(actionAttribute) && StringUtils.equals(actionAttribute, "function")) {
					String functionNameAttribute = this.getAttribute(nodeList.item(i), "function_name");
					this.functionStart = new HashMap<String, String>();
					functionStart.put("control", "comment");
					functionStart.put("comment", "Starting Function:"+functionNameAttribute);
					nodes.add(functionStart);
					String subFunction = this.getAttribute(nodeList.item(i), "sub_function");
					if(null != subFunction && subFunction.equalsIgnoreCase("false")){
						this.subFunctionMap = new HashMap<String, String>();
						subFunctionMap.put("control","data_input");
						subFunctionMap.put("data_inputs", this.getAttribute(nodeList.item(i), "data_inputs"));
						subFunctionMap.put("data_types", this.getAttribute(nodeList.item(i), "data_types"));
						nodes.add(subFunctionMap);
					}
					if (StringUtils.isNotBlank(functionNameAttribute)) {
						recursiveCall(builder, functionNameAttribute,nodes);
					}
					functionStop = new HashMap<String, String>();
					functionStop.put("control", "comment");
					functionStop.put("comment", "Stopping Function:"+functionNameAttribute);
					nodes.add(functionStop);
				} else {
					stepsMap = this.getAttributes(nodeList.item(i));
					nodes.add(stepsMap);
				}
			}
		} catch (UTAFException e) {
			log.error("UTAFException occured during XML Parsing : "+e.getMessage());
		} catch (XPathExpressionException e) {
			log.error("XPathExpressionException :"+e);
			throw new UTAFException("XPathExpressionException occured during XML Parsing :" +e.getMessage());
		}
		return nodes;
	}

	private Document getRootNode(DocumentBuilder builder, String xmlPath) throws UTAFException{
		Document document = null;
		String testFileData;
		try {
			testFileData = XMLIncludeBuilder.buildIncludeXMLFromFile(xmlPath);
			if (StringUtils.isNotBlank(testFileData)) {
				InputStream is = new ByteArrayInputStream(testFileData.getBytes("UTF-8"));
				document = builder.parse(is);
			}
		} catch (TDMServiceException e) {
			log.error("TDMServiceException :"+e);
			throw new UTAFException("TDMServiceException occured during XML Parsing in getRootNode method :"+e.getMessage());
		} catch (UnsupportedEncodingException e) {
			log.error("UnsupportedEncodingException :"+e);
			throw new UTAFException("UnsupportedEncodingException occured during XML Parsing in getRootNode method :"+e.getMessage());
		} catch (SAXException e) {
			log.error("SAXException :"+e);
			throw new UTAFException("SAXException occured during XML Parsing in getRootNode method :"+e.getMessage());
		} catch (IOException e) {
			log.error("IOException :"+e);
			throw new UTAFException("IOException occured during XML Parsing in getRootNode method :"+e.getMessage());
		}

		return document;
	}

	private String getAttribute(Node node, String attributeName) {
		String result = null;
		if (StringUtils.isNotBlank(attributeName) && node.getAttributes() != null && node.getAttributes().getLength() > 0 &&
				node.getAttributes().getNamedItem(attributeName) != null &&
				StringUtils.isNotBlank(node.getAttributes().getNamedItem(attributeName).getNodeValue())) {
			result = node.getAttributes().getNamedItem(attributeName).getNodeValue();
		}
		return result;
	}

	private Map<String,String> getAttributes(Node node) throws UTAFException{
		this.attributeMap = new HashMap<String,String> ();	
		try {
			attributeMap = getChildNodeList(node,"comment",attributeMap);
			attributeMap = getChildNodeList(node,"expected_results/url",attributeMap);
			if (node.getAttributes() != null && node.getAttributes().getLength() > 0){
				for(int i =0; i<node.getAttributes().getLength() ; i++){
					attributeMap.put(node.getAttributes().item(i).getNodeName(), node.getAttributes().item(i).getNodeValue());
				}
			}
		} catch (UTAFException e) {
			log.error("XPathExpressionException :"+e);
			log.error("XPathExpressionException occured during XML Parsing : "+e.getMessage());
		}
		return attributeMap;
	}

	private Map<String,String> getChildNodeList(Node node, String expression,Map<String,String> childMap ) throws UTAFException{
		XPath xPath =  XPathFactory.newInstance().newXPath();
		NodeList childNodeList;
		try {
			childNodeList = (NodeList) xPath.compile(expression).evaluate(node, XPathConstants.NODESET);

			for(int cnl=0; cnl<childNodeList.getLength();cnl++){
				if(null != childNodeList.item(cnl)){
					childMap.put(childNodeList.item(cnl).getNodeName(), childNodeList.item(cnl).getTextContent());
				}
			}
		} catch (XPathExpressionException e) {
			log.error("XPathExpressionException :"+e);
			throw new UTAFException("XPathExpressionException occured during XML Parsing :"+e.getMessage());
		}
		return childMap;
	}

	public Map<String, String> getEnvironment(String envFile, String environmentId) throws UTAFException{
		InputStream is = this.getClass().getClassLoader().getResourceAsStream(envFile);
		DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder;
		try {
			builder = builderFactory.newDocumentBuilder();
			envMap = new HashMap<String, String>();
			Document document = builder.parse(is);
			XPath xPath =  XPathFactory.newInstance().newXPath();
			String expression = "//entry";
			NodeList nodeList = (NodeList) xPath.compile(expression).evaluate(document, XPathConstants.NODESET);
			for(int i=0; i<nodeList.getLength();i++){
				if(nodeList.item(i)  == null){
					continue;
				}
				String envKey = this.getAttribute(nodeList.item(i), "key");
				if(StringUtils.isNotBlank(envKey) && envKey.equalsIgnoreCase(environmentId)){
					NodeList childNodeList = nodeList.item(i) .getChildNodes();
					for(int j=0; j<childNodeList.getLength() ; j++){
						envMap.put(childNodeList.item(j).getNodeName(), childNodeList.item(j).getTextContent());
					}
				}
			}
		} catch (ParserConfigurationException e) {
			log.error("ParserConfigurationException :"+e);
			throw new UTAFException("ParserConfigurationException occured during XML Parsing in getEnvironment method :"+e.getMessage());
		} catch (SAXException e) {
			log.error("SAXException :"+e);
			throw new UTAFException("SAXException occured during XML Parsing in getEnvironment method :"+e.getMessage());
		} catch (IOException e) {
			log.error("IOException :"+e);
			throw new UTAFException("IOException occured during XML Parsing in getEnvironment method :"+e.getMessage());
		} catch (XPathExpressionException e) {
			log.error("XPathExpressionException :"+e);
			throw new UTAFException("XPathExpressionException occured during XML Parsing in getEnvironment method :"+e.getMessage());
		}
		return envMap;
	}

	@SuppressWarnings("rawtypes")
	public String getEnvValue(String envKeyId) {
		String value = null;
		Iterator<?> it = envMap.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry pairs = (Map.Entry) it.next();
			log.info("Environment file property data : " +pairs.getKey() + " = " + pairs.getValue());
			if (pairs.getKey().toString().equalsIgnoreCase(envKeyId)) {
				value = pairs.getValue().toString();
				break;
			}
		}
		return value;
	}
	
	public String parseData(String fileData, String expression) throws UTAFException{
		DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder;
		String data = null;
		try {
			builder = builderFactory.newDocumentBuilder();
			this.nodes = new ArrayList<Map<String, String>>();
			if (StringUtils.isNotBlank(fileData)) {
				InputStream is = new ByteArrayInputStream(fileData.getBytes("UTF-8"));
				Document document = builder.parse(is);
				XPath xPath =  XPathFactory.newInstance().newXPath();
				NodeList dataNode = (NodeList) xPath.compile(expression).evaluate(document, XPathConstants.NODESET);
				data = dataNode.item(0).getTextContent();
			}
		} catch (ParserConfigurationException e) {
			log.error("ParserConfigurationException :"+e);
			throw new UTAFException("ParserConfigurationException occured during XML Parsing in parseData method :"+e.getMessage());
		} catch (UnsupportedEncodingException e) {
			log.error("UnsupportedEncodingException :"+e);
			throw new UTAFException("UnsupportedEncodingException occured during XML Parsing in parseData method :"+e.getMessage());
		} catch (SAXException e) {
			log.error("SAXException :"+e);
			throw new UTAFException("SAXException occured during XML Parsing in parseData method :"+e.getMessage());
		} catch (IOException e) {
			log.error("IOException :"+e);
			throw new UTAFException("IOException occured during XML Parsing in parseData method :"+e.getMessage());
		} catch (XPathExpressionException e) {
			log.error("XPathExpressionException :"+e);
			throw new UTAFException("XPathExpressionException occured during XML Parsing in parseData method :"+e.getMessage());
		}
		return data;	
	}

	public void destroyHashMap(){
		nodes.clear();
		stepsMap.clear();
		functionStart.clear();
		subFunctionMap.clear();
		functionStop.clear();
		attributeMap.clear();
	}
}
