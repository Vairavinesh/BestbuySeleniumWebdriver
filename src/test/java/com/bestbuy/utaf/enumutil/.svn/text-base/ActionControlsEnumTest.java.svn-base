package com.bestbuy.utaf.enumutil;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.HashMap;

import javax.xml.parsers.ParserConfigurationException;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.xml.sax.SAXException;

import com.bestbuy.utaf.exceptions.UTAFException;
import com.bestbuy.utaf.testcaserunner.StepValidator;
import com.bestbuy.utaf.xmlparser.VTDXMLParser;
/**
 * @author Kalaiselvi Jaganathan
 *
 */
public class ActionControlsEnumTest {
	
	ActionControlsEnum actionControlsEnum;
	
	@Mock
	StepValidator stepValidator;
	
	@Mock
	VTDXMLParser vtdTestcaseXmlParser;
	
	@Before
	public void init() {
		stepValidator = mock(StepValidator.class);
		vtdTestcaseXmlParser = mock(VTDXMLParser.class);
	}
	
	@SuppressWarnings("static-access")
	@Test
	public void testVisible() throws ParserConfigurationException, SAXException, IOException, UTAFException {
		HashMap<String, String> stepControl = null;
		when(stepValidator.visible(Mockito.anyString())).thenReturn("Visible");
		String xpath="";
		String message = actionControlsEnum.valueOf("VISIBLE").execute(stepControl, stepValidator,xpath);
		assertEquals("Visible",message);
		
	}
	
	@SuppressWarnings("static-access")
	@Test
	public void testValidateUrl() throws ParserConfigurationException, SAXException, IOException, UTAFException {
		HashMap<String, String> stepControl = new HashMap<String, String>();
		String xpath="";
		stepControl.put("expected_results", "data_input");
		String message = actionControlsEnum.valueOf("VALIDATE_URL").execute(stepControl, stepValidator,xpath);
		assertEquals("",message);
	}
	
	@SuppressWarnings("static-access")
	@Test
	public void testValidateUrlCondition() throws ParserConfigurationException, SAXException, IOException, UTAFException {
		HashMap<String, String> stepControl = new HashMap<String, String>();
		String xpath="";
		stepControl.put("expected_results", "data");
		stepControl.put("data_input", "data_input");
		String message = actionControlsEnum.valueOf("VALIDATE_URL").execute(stepControl, stepValidator,xpath);
		assertNull(message);
	}
	
	@SuppressWarnings("static-access")
	@Test
	public void testValidateLabel() throws ParserConfigurationException, SAXException, IOException, UTAFException {
		HashMap<String, String> stepControl = new HashMap<String, String>();
		String xpath="";
		stepControl.put("expected_results", "data_input");
		when(stepValidator.validateLabel(Mockito.anyString(),Mockito.anyString())).thenReturn("ValidateLabel");
		String message = actionControlsEnum.valueOf("VALIDATE_LABEL").execute(stepControl, stepValidator,xpath);
		assertEquals("",message);
	}
	
	@SuppressWarnings("static-access")
	@Test
	public void testValidateLabelCondition() throws ParserConfigurationException, SAXException, IOException, UTAFException {
		HashMap<String, String> stepControl = new HashMap<String, String>();
		String xpath="";
		stepControl.put("expected_results", "data");
		stepControl.put("data_input", "data_input");
		when(stepValidator.validateLabel(Mockito.anyString(),Mockito.anyString())).thenReturn("ValidateLabel");
		String message = actionControlsEnum.valueOf("VALIDATE_LABEL").execute(stepControl, stepValidator,xpath);
		assertEquals("ValidateLabel",message);
	}

	
	@SuppressWarnings({ "static-access", "unchecked" })
	@Test
	public void testValidateText() throws ParserConfigurationException, SAXException, IOException, UTAFException {
		HashMap<String, String> stepControl = new HashMap<String, String>();
		stepControl.put("text", "data_input");
		String xpath="";
		when(stepValidator.validateText(Mockito.anyMap(), Mockito.anyString(),Mockito.anyString())).thenReturn("validateTextInput");
		String message = actionControlsEnum.valueOf("VALIDATE_TEXT").execute(stepControl, stepValidator,xpath);
		assertEquals("",message);
	}
	
	@SuppressWarnings({ "static-access", "unchecked" })
	@Test
	public void testValidateTextCondition() throws ParserConfigurationException, SAXException, IOException, UTAFException {
		HashMap<String, String> stepControl = new HashMap<String, String>();
		stepControl.put("text", "data");
		stepControl.put("data_input", "data_input");
		String xpath="";
		when(stepValidator.validateText(Mockito.anyMap(), Mockito.anyString(),Mockito.anyString())).thenReturn("validateTextInput");
		String message = actionControlsEnum.valueOf("VALIDATE_TEXT").execute(stepControl, stepValidator,xpath);
		assertEquals("validateTextInput",message);
	}
	
	@SuppressWarnings({ "static-access", "unchecked" })
	@Test
	public void testValidatePageTitle() throws ParserConfigurationException, SAXException, IOException, UTAFException {
		HashMap<String, String> stepControl = new HashMap<String, String>();
		stepControl.put("text", "data_input");
		String xpath="";
		when(stepValidator.validateText(Mockito.anyMap(),Mockito.anyString(),Mockito.anyString())).thenReturn("validateTextInput");
		String message = actionControlsEnum.valueOf("VALIDATE_PAGETITLE").execute(stepControl, stepValidator,xpath);
		assertEquals("",message);
	}
	
	@SuppressWarnings({ "static-access", "unchecked" })
	@Test
	public void testValidatePageTitleCondition() throws ParserConfigurationException, SAXException, IOException, UTAFException {
		HashMap<String, String> stepControl = new HashMap<String, String>();
		stepControl.put("text", "data");
		String xpath="";
		stepControl.put("data_input", "data_input");
		when(stepValidator.validateText(Mockito.anyMap(), Mockito.anyString(), Mockito.anyString())).thenReturn("validateTextInput");
		String message = actionControlsEnum.valueOf("VALIDATE_PAGETITLE").execute(stepControl, stepValidator,xpath);
		assertEquals("validateTextInput",message);
	}
	
	@SuppressWarnings("static-access")
	@Test
	public void testEnum(){
		assertTrue(actionControlsEnum.enumContains("VALIDATE_PAGETITLE"));
	}
	
	@SuppressWarnings("static-access")
	@Test
	public void testEnumFalse(){
		assertFalse(actionControlsEnum.enumContains("PAGE"));
	}
}
