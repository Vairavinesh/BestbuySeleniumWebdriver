package com.bestbuy.utaf.xmlparser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
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
import org.junit.Before;
import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.bestbuy.fta.basedataframework.common.exception.TDMServiceException;
import com.bestbuy.fta.basedataframework.xsd.XMLIncludeBuilder;
import com.ximpleware.NavException;
import com.ximpleware.XPathEvalException;
import com.ximpleware.XPathParseException;

/**
 * @author BestBuy Inc
 * Unit test for VTDXMLParser
 */
public class VTDXMLParserTest {

	VTDXMLParser vtdXMLParser;
	String xPath;

	@Before
	public void init() throws FileNotFoundException {
		String testFile = "src/test/resources/Functions/appTest1/FunctionBuilderTestLvl1.xml";
		File f1 = new File(testFile);
		vtdXMLParser= new VTDXMLParser(f1.getAbsolutePath());
		xPath = "//*/step";
	}

	@Test
	public void testConstructorString() throws FileNotFoundException{
		vtdXMLParser= new VTDXMLParser("<?xml version=\"1.0\" encoding=\"UTF-8\"?><testcase xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:noNamespaceSchemaLocation=\"src/test/resources/Tests/schemas/Function.xsd\"></testcase>");
		assertNotNull(vtdXMLParser.getXmlDoc().toString());
	}

	@Test
	public void testConstructorFileNotFound() throws FileNotFoundException{
		String testFile = "src/test/resources/Functions/appTest1/FunctionBuilderTestLvl1332.xml";
		File f1 = new File(testFile);
		vtdXMLParser= new VTDXMLParser(f1.getAbsolutePath());
		assertNotNull(vtdXMLParser.getXmlDoc().toString());
	}



	@Test
	public void testXpathExistsTrue(){
		boolean value = vtdXMLParser.xpathExists(xPath);
		assertEquals(true, value);
	}

	@Test
	public void testXpathExistsFalse(){
		String xPath = "//*/step&";
		boolean value = vtdXMLParser.xpathExists(xPath);
		assertEquals(false, value);
	}

	@Test
	public void testDestroy(){
		vtdXMLParser.destroy();
		assertEquals(null, vtdXMLParser.getXmlNav());
	}

	

	@Test
	public void testGetAllTextNodesSucess() throws XPathParseException, XPathEvalException, NavException{
		Map<String, String> nodes = vtdXMLParser.getAllTextNodes(xPath);
		assertEquals(1, nodes.size());
	}

	

	public static HashMap<String, String> getExpectedResults() throws IOException {
		HashMap<String, String> result = new HashMap<String, String>();
		String path = "src/test/resources/ExpectedResultsForVtdTestcaseParser/VtdTestcaseParserTest.txt";
		FileInputStream fstream = new FileInputStream(path);
		DataInputStream in = new DataInputStream(fstream);
		BufferedReader br = new BufferedReader(new InputStreamReader(in));
		String strLine;
		int cnt = 0;
		while ((strLine = br.readLine()) != null) {
			String[] line = strLine.split("#");
			result.put(line[0], line[1]);
			cnt = cnt + 1;
		}
		br.close();
		in.close();
		return result;
	}


	@Test
	public void testDOMParser() throws XPathExpressionException, ParserConfigurationException, SAXException, IOException, TDMServiceException{
		DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = builderFactory.newDocumentBuilder();
		String xmlPath = "/Users/a922998/objectdb_bk/kalai/xml/testcase/testcase.xml";
		List<HashMap<String, String>> nodes = new ArrayList<HashMap<String, String>>();
		nodes = this.recursiveCall(builder, xmlPath,nodes);
		assertEquals(15,nodes.size());
	}

	private List<HashMap<String, String>>  recursiveCall(DocumentBuilder builder, String xmlPath,List<HashMap<String, String>> nodes) throws XPathExpressionException, TDMServiceException, SAXException, IOException {
		HashMap<String, String> stepsMap = new HashMap<String, String>();
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
				HashMap<String, String> functionStart = new HashMap<String, String>();
				functionStart.put("control", "comment");
				functionStart.put("comment", "Starting Function:"+functionNameAttribute);
				nodes.add(functionStart);
				String subFunction = this.getAttribute(nodeList.item(i), "sub_function");
				if(null != subFunction && subFunction.equalsIgnoreCase("false")){
					HashMap<String, String> subFunctionMap = new HashMap<String, String>();
					subFunctionMap.put("control","data_input");
					subFunctionMap.put("data_inputs", this.getAttribute(nodeList.item(i), "data_inputs"));
					subFunctionMap.put("data_types", this.getAttribute(nodeList.item(i), "data_types"));
					nodes.add(subFunctionMap);
				}
				if (StringUtils.isNotBlank(functionNameAttribute)) {
					recursiveCall(builder, functionNameAttribute,nodes);
				}
				HashMap<String, String> functionStop = new HashMap<String, String>();
				functionStop.put("control", "comment");
				functionStop.put("comment", "Stopping Function:"+functionNameAttribute);
				nodes.add(functionStop);
			} else {
				stepsMap = this.getAttributes(nodeList.item(i));
				nodes.add(stepsMap);
			}
		}
		return nodes;
	}

	private Document getRootNode(DocumentBuilder builder, String xmlPath) throws TDMServiceException, SAXException, IOException, XPathExpressionException {
		Document document = null;
		String testFileData = XMLIncludeBuilder.buildIncludeXMLFromFile(xmlPath);
		if (StringUtils.isNotBlank(testFileData)) {
			InputStream is = new ByteArrayInputStream(testFileData.getBytes("UTF-8"));
			document = builder.parse(is);
		}
		return document;
	}

	private String getAttribute(Node node, String attributeName) {
		String result = null;
		if (StringUtils.isNotBlank(attributeName)) {
			if (node.getAttributes() != null && node.getAttributes().getLength() > 0 &&
					node.getAttributes().getNamedItem(attributeName) != null &&
					StringUtils.isNotBlank(node.getAttributes().getNamedItem(attributeName).getNodeValue())) {
				result = node.getAttributes().getNamedItem(attributeName).getNodeValue();
			}
		}
		return result;
	}

	private HashMap<String,String> getAttributes(Node node) throws XPathExpressionException{
		HashMap<String,String> stepMap = new HashMap<String,String> ();	
		stepMap = getChildNodeList(node,"comment",stepMap);
		stepMap = getChildNodeList(node,"expected_result",stepMap);
		if (node.getAttributes() != null && node.getAttributes().getLength() > 0){
			for(int i =0; i<node.getAttributes().getLength() ; i++){
				stepMap.put(node.getAttributes().item(i).getNodeName(), node.getAttributes().item(i).getNodeValue());
			}
		}
		return stepMap;
	}
	
	private HashMap<String,String> getChildNodeList(Node node, String expression,HashMap<String,String> stepMap ) throws XPathExpressionException{
		XPath xPath =  XPathFactory.newInstance().newXPath();
		NodeList commentNodeList = (NodeList) xPath.compile(expression).evaluate(node, XPathConstants.NODESET);
		for(int cnl=0; cnl<commentNodeList.getLength();cnl++){
			if(null != commentNodeList.item(cnl)){
				stepMap.put(commentNodeList.item(cnl).getNodeName(), commentNodeList.item(cnl).getTextContent());
			}
		}
		return stepMap;
	}
	
	@Test
	public void testEnvParser() throws XPathExpressionException, ParserConfigurationException, SAXException, IOException, TDMServiceException{
		DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = builderFactory.newDocumentBuilder();
		String xmlPath = "/Users/a922998/TestEng/UTAF_SVNS/src/main/resources/Environment_Configuration/EnvironmentDefinitions.xml";
		HashMap<String, String> nodes = new HashMap<String, String>();
		Document document = builder.parse(xmlPath);
		XPath xPath =  XPathFactory.newInstance().newXPath();
		String expression = "//entry";
		NodeList nodeList = (NodeList) xPath.compile(expression).evaluate(document, XPathConstants.NODESET);
		for(int i=0; i<nodeList.getLength();i++){
			if(nodeList.item(i)  == null){
				continue;
			}
			String envKey = this.getAttribute(nodeList.item(i), "key");
			if(StringUtils.isNotBlank(envKey) && envKey.equalsIgnoreCase("QA2")){
				NodeList childNodeList = nodeList.item(i) .getChildNodes();
				for(int j=0; j<childNodeList.getLength() ; j++){
					nodes.put(childNodeList.item(j).getNodeName(), childNodeList.item(j).getTextContent());
				}
			}
		}
		System.out.println(nodes.size());
	}

}
