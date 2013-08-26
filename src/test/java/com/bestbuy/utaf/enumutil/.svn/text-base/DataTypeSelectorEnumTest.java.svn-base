/**
 * 
 */
package com.bestbuy.utaf.enumutil;

import static org.junit.Assert.fail;

import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import com.bestbuy.utaf.exceptions.UTAFException;
import com.ximpleware.NavException;
import com.ximpleware.XPathEvalException;
import com.ximpleware.XPathParseException;

/**
 * @author a922998
 *
 */
public class DataTypeSelectorEnumTest {


	@Test
	public void test() throws XPathParseException, XPathEvalException, NavException, UTAFException {
		Map<String, String> tdmMap =  DataTypeSelectorEnum.valueOf("TDM").execute("QRY_AGE_VERIFICATION_SKU", "qa1");
		System.out.println(tdmMap.size());
		Assert.assertEquals(3, tdmMap.size());

	}
	
	@Test
	public void testTDMUser() throws XPathParseException, XPathEvalException, NavException, UTAFException {
		Map<String, String> tdmMap =  DataTypeSelectorEnum.valueOf("TDM").execute("USR_REGISTERED_USER", "qa1");
		System.out.println(tdmMap.size());
		Assert.assertEquals(2, tdmMap.size());
	}

}
