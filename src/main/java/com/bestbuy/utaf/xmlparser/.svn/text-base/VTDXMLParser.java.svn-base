package com.bestbuy.utaf.xmlparser;

import java.io.EOFException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.ximpleware.AutoPilot;
import com.ximpleware.EncodingException;
import com.ximpleware.EntityException;
import com.ximpleware.NavException;
import com.ximpleware.VTDGen;
import com.ximpleware.VTDNav;
import com.ximpleware.XPathEvalException;
import com.ximpleware.XPathParseException;

/**
 * XML Parser
 * 
 * @author Best Buy Inc
 * 
 */
public class VTDXMLParser {
	private static Logger log = Logger.getLogger("VTDXMLParser");

	private VTDGen xmlDoc;
	private String file = "";
	private AutoPilot ap;
	private VTDNav xmlNav;
	
	/**
	 * @return the xmlNav
	 */
	public VTDNav getXmlNav() {
		return xmlNav;
	}

	/**
	 * @param xmlNav the xmlNav to set
	 */
	public void setXmlNav(VTDNav xmlNav) {
		this.xmlNav = xmlNav;
	}

	/**
	 * @return the xmlDoc
	 */
	public VTDGen getXmlDoc() {
		return xmlDoc;
	}

	/**
	 * @param xmlDoc the xmlDoc to set
	 */
	public void setXmlDoc(VTDGen xmlDoc) {
		this.xmlDoc = xmlDoc;
	}
	
	/**
	 * Constructor : Read from file
	 * 
	 * @param file
	 * @throws FileNotFoundException
	 * @throws UnsupportedEncodingException
	 */
	public VTDXMLParser(String file) throws FileNotFoundException {
		this.xmlDoc = new VTDGen();
		File input = new File(file);
		if(!input.isFile()){	
			try {
				this.xmlDoc.setDoc(file.getBytes());
				this.xmlDoc.parse(false);
				this.xmlNav = xmlDoc.getNav();
				this.ap = new AutoPilot(xmlNav);
				this.ap.bind(xmlNav);
			} catch (Exception e) {
				log.error("Exception occured in VTD Parser :"+e.getMessage());
			}
		}else{
			this.file = file;
			File f = new File(this.file);
			if (f.exists()) {
				if (this.xmlDoc.parseFile(f.getAbsolutePath(), true)) {
					this.xmlNav = xmlDoc.getNav();
					this.ap = new AutoPilot(xmlNav);
					this.ap.bind(xmlNav);
				}
			} else {
				throw new FileNotFoundException(this.file+ " file does not exist!!!");
			}
		}
	}

	/**
	 * Destroy all
	 */
	public void destroy() {
		this.file = "";
		this.ap.resetXPath();
		this.xmlNav = null;
		this.xmlDoc.clear();

	}

	/**
	 * Check whether the given xPath exists or not
	 * 
	 * @param xPath
	 * @return boolean
	 */
	public boolean xpathExists(String xPath) {
		boolean found = false;
		try {
			this.ap.selectXPath(xPath);
			found = this.ap.evalXPathToBoolean();
		} catch (XPathParseException xPathParseexcep) {
			log.error("XPathParse Exception occured in VTDXMLParser : "
					+ xPathParseexcep.getMessage());
		}
		return found;
	}

	

	/**
	 * Get all the text nodes in within a xml
	 * 
	 * @param xPath
	 * @return HashMap<String, String>
	 * @throws XPathParseException
	 * @throws XPathEvalException
	 * @throws NavException
	 */
	public Map<String, String> getAllTextNodes(String xPath)
			throws XPathParseException, XPathEvalException, NavException {
		Map<String, String> nodes = new HashMap<String, String>();
		File t = new File("src/test/temp");
		t.getAbsolutePath();
		try {
			this.ap.selectXPath(xPath);
			int nodeCnt = 0;
			int i = 0;
			while ((i = this.ap.evalXPath()) != -1) {
				AutoPilot ap2 = new AutoPilot(this.xmlNav);
				ap2.selectAttr("*");
				nodes.put(this.xmlNav.toString(i),
						this.xmlNav.toString(this.xmlNav.getText()));
				ap2 = null;
				// fileout.logFailure(tmp, 8192);
				nodeCnt = nodeCnt + 1;
			}
		} catch (XPathParseException pathParseExcep) {
			log.error("XPathParse Exception occured getTests method in VTDXMLParser : "
					+ pathParseExcep.getMessage());
			throw new XPathParseException(xPath);
		} catch (XPathEvalException pathValExcep) {
			log.error("XPathEval Exception occured getTests method in VTDXMLParser : "
					+ pathValExcep.getMessage());
			throw new XPathEvalException(xPath);
		} catch (NavException navException) {
			log.error("Nav Exception occured getTests method in VTDXMLParser : "
					+ navException.getMessage());
			throw new NavException(xPath);
		}
		return nodes;
	}


	/**
	 * Get all element ids for elements matching specified xpath.
	 * 
	 * @param xPath
	 * @return List<String>
	 * @throws XPathParseException
	 * @throws XPathEvalException
	 * @throws NavException
	 * @throws IOException
	 * @throws ParseException
	 * @throws EntityException
	 * @throws EOFException
	 * @throws EncodingException
	 */
	public List<String> getNodeMetaDataNamesStartingAtRoot(String xpath,
			String elementType, String idString) throws NavException, XPathParseException,
			XPathEvalException {
		List<String> nodes = new ArrayList<String>();

		xmlNav.toElement(VTDNav.ROOT);
		AutoPilot ap = new AutoPilot(this.xmlNav);

		@SuppressWarnings("unused")
		int i;

		ap.selectXPath("//*");

		while ((i = ap.evalXPath()) != -1) {
			AutoPilot ap2 = new AutoPilot(this.xmlNav);
			ap2.selectAttr("*");
			int j = -1;

			while ((j = ap2.iterateAttr()) != -1) {
				String text = "";
			//	System.out.println(this.xmlNav.toString(j).contains("type") + "  " +  this.xmlNav.toString(j + 1));
				if (elementType.equals("link")) {
					text = getLinkText(elementType, j, idString);
					if (text == null) {
						break;
					}
				} else if (elementType.equals("button") && (this.xmlNav.toString(j).contains("type") && this.xmlNav.toString(j + 1).matches(idString))) {
						text = getButtonLabel();
				} else if (elementType.equals("checkbox")) {
					
				}  else if (elementType.equals("textbox")) {
					
				}  else if (elementType.equals("radio button") && (this.xmlNav.toElement(VTDNav.FIRST_CHILD)    && this.xmlNav.toString(j + 1).matches(idString))) {
					text = getRadioBtnLabel();
				} else {
					
				}
				if (StringUtils.isNotEmpty(text) && !nodes.contains(text)) {
					nodes.add(text);
				}

			}
		}
		ap.resetXPath();
		return nodes;
	}

	private String getRadioBtnLabel() {
		String text = "";
		
		
		return text;
	}
	
	private String getButtonLabel( ) throws NavException {
		String text = "";
		if (this.xmlNav.hasAttr("value")) {
			text = this.xmlNav.toNormalizedString(this.xmlNav
					.getAttrVal("value"));
		} else {	
			text = this.xmlNav.toString(this.xmlNav.getAttrVal("class")).split("-")[0];
		}	
		return text;
	}
	
	private String getLinkText(String elementType, int attribNum, String idString)
			throws NavException {
		String text = "";
		if (this.xmlNav.hasAttr("class")
				&& this.xmlNav.toString(this.xmlNav.getAttrVal("class"))
						.contains("content")) {
			text = null;
		} else if (this.xmlNav.toString(attribNum + 1).matches(idString)
				|| this.xmlNav.hasAttr("href")) {
			int testText = this.xmlNav.getText();
			if (testText != -1) {
				text = this.xmlNav.toNormalizedString(this.xmlNav.getText());
			} else {
				this.xmlNav.toElement(VTDNav.FIRST_CHILD);
				testText = this.xmlNav.getText();
				if (testText != -1) {
					text = this.xmlNav
							.toNormalizedString(this.xmlNav.getText());

				} else if (this.xmlNav.hasAttr("id")) {
					text = StringUtils.capitalize(this.xmlNav
							.toNormalizedString(xmlNav.getAttrVal("id")));
				}
			}
		}
		return text;
	}
}
