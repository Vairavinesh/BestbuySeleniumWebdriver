package com.bestbuy.utaf.xmlparser;

import static org.junit.Assert.assertEquals;

import java.util.List;
import java.util.Map;

import org.junit.Test;

import com.bestbuy.utaf.exceptions.UTAFException;

/**
 * @author a922998
 *
 */
public class XMLParserTest {

	@Test
	public void test() throws UTAFException {
		XMLParser xmlParser = new XMLParser();
		String xmlPath ="/Users/a922998/objectdb_bk/kalai/xml/testcase/testcase.xml";
		List<Map<String, String>>  xmlList = xmlParser.parseXML(xmlPath);
		assertEquals(15,xmlList.size());
		xmlParser.destroyHashMap();
	}

}
