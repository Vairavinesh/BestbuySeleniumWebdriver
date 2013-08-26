package com.bestbuy.utaf.util;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.io.InputStream;
import java.util.InvalidPropertiesFormatException;

import org.junit.Before;
import org.junit.Test;

import com.bestbuy.utaf.exceptions.UTAFException;

/**
 * @author BestBuy Inc
 *
 */
public class DriverPropsTest {
	
	DriverProps driverProps;
	
	@Before
	public void init() throws InvalidPropertiesFormatException, UTAFException{
		InputStream is = this.getClass().getClassLoader().getResourceAsStream("Driver_Properties/driverpoc_properties.xml");
		driverProps = new DriverProps(is);
	}

	/**
	 * Unit test to read the property value from the file
	 * @throws IOException
	 */
	@Test
	public void testGetPropValue() throws IOException {
		String propValue = driverProps.getPropValue("masterXML");
		assertEquals("src/test/resources/Tests/Runners/MasterXMLSample.xml",propValue);
	}
	
	/**
	 * Unit test with correct file but invaid key
	 * @throws IOException
	 */
	@Test
	public void testInvalidPropKey() throws IOException {
		String propValue = driverProps.getPropValue("masterXMLhgfdse");
		assertEquals(null,propValue);
	}
	
	/**
	 * Unit test - pass the parameter file which doesnt exist
	 * @throws IOException
	 */
	@Test(expected=IOException.class)
	public void testIOException() throws UTAFException {
		InputStream is = this.getClass().getClassLoader().getResourceAsStream("Driver_Properties/driverpoc_properties.xml");
		driverProps = new DriverProps(is);
		driverProps.getPropValue("masterXML");
	}
	


}
