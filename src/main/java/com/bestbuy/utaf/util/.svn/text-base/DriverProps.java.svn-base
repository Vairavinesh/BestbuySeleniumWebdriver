package com.bestbuy.utaf.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.InvalidPropertiesFormatException;
import java.util.Properties;

import com.bestbuy.utaf.exceptions.UTAFException;

/**
 * @author thomas
 */

public class DriverProps {

	private java.util.Properties prop = new Properties();
	
	/**
	 * Constructor
	 * @param file
	 * @throws UTAFException 
	 * @throws IOException 
	 * @throws InvalidPropertiesFormatException 
	 */
	public DriverProps(InputStream is) throws UTAFException{
		try {
			this.prop = new Properties();
			this.prop.loadFromXML(is);
		} catch (InvalidPropertiesFormatException e) {
			throw new UTAFException("InvalidPropertiesFormatException occured in DriverProps :"+e.getMessage());
		} catch (IOException e) {
			throw new UTAFException("IOException occured in DriverProps :"+e.getMessage());
		}
	}
	
	/**
	 * Get Property Value for the nodeId
	 * @param nodeId
	 * @return String
	 * @throws IOException 
	 */
	public String getPropValue(String nodeId){
		return prop.getProperty(nodeId);
	}

}
