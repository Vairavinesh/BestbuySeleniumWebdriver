/**
 * 
 */
package com.bestbuy.utaf.util;

import java.io.File;
import java.io.IOException;
import org.htmlcleaner.CleanerProperties;
import org.htmlcleaner.HtmlCleaner;
import org.htmlcleaner.PrettyXmlSerializer;
import org.htmlcleaner.TagNode;





/**
 * @author a948621
 *
 */
public class HtmlClean {

	public void cleanHtml(String file, String outputPath) throws IOException  {
		CleanerProperties props = new CleanerProperties();
		File f1 = new File(file);
		// set some properties to non-default values
		props.setTranslateSpecialEntities(true);
		props.setTransResCharsToNCR(true);
		props.setOmitComments(true);
	//	props.setUseCdataForScriptAndStyle(true);
		props.setAllowHtmlInsideAttributes(true);
		props.setAdvancedXmlEscape(false);
		props.setAllowMultiWordAttributes(true);
		props.setNamespacesAware(true);
		props.setTreatUnknownTagsAsContent(true);
		props.setOmitUnknownTags(true);
		props.setUseCdataForScriptAndStyle(false);
		 
		// do parsing
		TagNode tagNode = new HtmlCleaner(props).clean(
		    f1);
		 
		// serialize to xml file
	LocalFileIO io = new LocalFileIO();
	io.crtFolder(outputPath);
	io.crtFile( outputPath + f1.getName());
		new PrettyXmlSerializer(props).writeToFile(
		    tagNode, outputPath + f1.getName(), "utf-8");
	}
}
