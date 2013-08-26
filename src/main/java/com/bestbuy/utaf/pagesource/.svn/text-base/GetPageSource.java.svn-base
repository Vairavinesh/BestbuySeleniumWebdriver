package com.bestbuy.utaf.pagesource;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.apache.commons.lang.WordUtils;

@SuppressWarnings("static-access")
public class GetPageSource {

	private static Logger log = Logger.getLogger("getPageSource");
	
	public static void savePageSource(String elementName, String fileName,
			int responseCode) {
		String resCode = Integer.toString(responseCode);
		if (resCode.equals("200")) {

			log.info("SAVING PAGE SOURCE FOR ::  " + fileName);
		} else {
			log.info("SAVING PAGE SOURCE FOR ::  " + fileName
					+ ".xml  the file is empty because response code is "
					+ responseCode + " but it should be 200");
		}
			try {
				if (elementName != null) {
					File f = new File(GetCurrentDir.getPageSrcCurrentDirectory()
							+ File.separator + fileName + ".xml");
					FileWriter fw = new FileWriter(f.getAbsoluteFile());
					BufferedWriter bw = new BufferedWriter(fw);
					bw.write(WordUtils.wrap(elementName, 50));
					bw.close();
				}
			} catch (Exception e) {
				log.info("Exception in savePageSource "+e.getMessage());
			}
		
	}

	public static void saveHiddenPageSource(String elementName,
			String fileName, int responseCode)  {
		String resCode = Integer.toString(responseCode);
		if (resCode.equals("200")) {

			log.info("SAVING HIDDEN PAGE SOURCE FOR ::  " + fileName);
		} else {
			log.info("SAVING HIDDEN PAGE SOURCE FOR ::  " + fileName
					+ ".xml  the file is empty because response code is "
					+ responseCode + " but it should be 200");
		}
		try {
			if (elementName != null) {
				File f = new File(GetCurrentDir.getHiddenPageSrcDirectory()
						+ File.separator + fileName + ".xml");

				FileWriter fw = new FileWriter(f.getAbsoluteFile());
				BufferedWriter bw = new BufferedWriter(fw);
				bw.write(WordUtils.wrap(elementName, 50));
				bw.close();
			}
		}  catch (Exception e) {
			log.info("Exception in saveHiddenPageSource "+e.getMessage());
		}
	}

	public static void saveToolTipPageSource(String elementName,
			String fileName, int responseCode)  {
		String resCode = Integer.toString(responseCode);
		if (resCode.equals("200")) {

			log.info("SAVING TOOLTIP PAGE SOURCE FOR ::  " + fileName);
		} else {
			log.info("SAVING TOOLTIP PAGE SOURCE FOR ::  " + fileName
					+ ".xml  the file is empty because response code is "
					+ responseCode + " but it should be 200");
		}
		try {
			if (elementName != null) {
				File f = new File(GetCurrentDir.getTooltipPageSrcDirectory()
						+ File.separator + fileName + ".xml");

				FileWriter fw = new FileWriter(f.getAbsoluteFile());
				BufferedWriter bw = new BufferedWriter(fw);
				bw.write(elementName);
				bw.close();
			}
		} catch (Exception e) {
			log.info("Exception in saveToolTipPageSource "+e.getMessage());
		}
	}

	public static void saveLightBoxPageSource(String elementName,
			String fileName, int responseCode)  {
		String resCode = Integer.toString(responseCode);
		if (resCode.equals("200")) {

			log.info("SAVING LightBox PAGE SOURCE FOR ::  " + fileName);
		} else {
			log.info("SAVING LightBox PAGE SOURCE FOR ::  " + fileName
					+ ".xml  the file is empty because response code is "
					+ responseCode + " but it should be 200");
		}
		try {
			if (elementName != null) {
				File f = new File(GetCurrentDir.getLightBoxDirectory()
						+ File.separator + fileName + ".xml");

				FileWriter fw = new FileWriter(f.getAbsoluteFile());
				BufferedWriter bw = new BufferedWriter(fw);
				bw.write(elementName);
				bw.close();
			}
		} catch (Exception e) {
			log.info("Exception in savelightBoxPageSource "+e.getMessage());
		}
	}
	
	public static void savePaginationSource(String elementName,
			String fileName, int responseCode)  {
		String resCode = Integer.toString(responseCode);
		if (resCode.equals("200")) {

			log.info("SAVING PAGINATION PAGE SOURCE FOR ::  " + fileName);
		} else {
			log.info("SAVING PAGINATION PAGE SOURCE FOR ::  " + fileName
					+ ".xml  the file is empty because response code is "
					+ responseCode + " but it should be 200");
		}
		try {
			if (elementName != null) {
				File f = new File(GetCurrentDir.getPaginationCurrentDirectory()
						+ File.separator + fileName + ".xml");

				FileWriter fw = new FileWriter(f.getAbsoluteFile());
				BufferedWriter bw = new BufferedWriter(fw);
				bw.write(elementName);
				bw.close();
			}
		} catch (Exception e) {
			log.info("Exception in savePaginationSource "+e.getMessage());
		}
	}
	
	
	private static int getPagesource(String fileName, String url,String sourceType)  {
        long WAITINGTIME = 5000;
		log.info("REQUEST URL ::   " + url);
		int responseCode = 0;
		try {
			URL obj = new URL(url);
			HttpURLConnection con = (HttpURLConnection) obj.openConnection();
			Thread.sleep(WAITINGTIME);
			con.setRequestMethod("GET");
			con.setRequestProperty("User-Agent", "Mozilla/22.0");
			responseCode = con.getResponseCode();
			log.info("RESPONSE CODE ::   " + responseCode);
			BufferedReader in = new BufferedReader(new InputStreamReader(
					con.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();
			StringBuffer hiddenResponse = new StringBuffer();
			StringBuffer tooltipResponse = new StringBuffer();
			StringBuffer paginationResponse = new StringBuffer();
			while ((inputLine = in.readLine()) != null) {
				if (inputLine.contains("hidden")) {
					hiddenResponse.append(inputLine);
				}
				if (inputLine.contains("alt=") && inputLine.contains("title=")) {
					tooltipResponse.append(inputLine);
				}
				if (inputLine.contains("next")) {
					paginationResponse.append(inputLine);
				}
				response.append(inputLine);
			}
			in.close();
			int count = countSymbol(sourceType);
			String[] sourceTypes = sourceType.split(",");
			for (int k = 0; k < count; k++) {
				String pageSourceTypes = sourceTypes[k];
				if (pageSourceTypes.equals("pagesource")) {
					savePageSource(response.toString(), fileName, responseCode);
				}
				if (pageSourceTypes.equals("hidden")) {
					saveHiddenPageSource(hiddenResponse.toString(), fileName,
							responseCode);
				}
				if (pageSourceTypes.equals("tooltip")) {
					saveToolTipPageSource(tooltipResponse.toString(), fileName,
							responseCode);
				}
				if (pageSourceTypes.equals("lightbox")) {
					saveLightBoxPageSource(response.toString(), fileName,
							responseCode);
				}
				if (pageSourceTypes.equals("pagination")) {
					savePaginationSource(paginationResponse.toString(), fileName,
							responseCode);
				}

			}
		} catch (MalformedURLException e) {
			log.info(" MalformedURLException in getPagesource "+e.getMessage());
		} catch (ProtocolException e) {
			log.info(" ProtocolException in getPagesource "+e.getMessage());
		} catch (IOException e) {
			log.info(" IOException in getPagesource "+e.getMessage());
		} catch (InterruptedException e) {
			log.info(" InterruptedException in getPagesource "+e.getMessage());
		}
		return responseCode;
	}

	public static List<Integer> pageSourceData() {
		List<Integer> responseCodes = new ArrayList<Integer>();
		try {
			PageSourceparser psp = new PageSourceparser();
			psp.executePageSourcexml();
			for (String urlName : psp.getPageSourceUrl().keySet()) {
				String url = psp.getPageSourceUrl().get(urlName);
				String sourcetype = psp.getPageSourceType().get(urlName);
				int responseCode = getPagesource(urlName, url, sourcetype);
				responseCodes.add(responseCode);
			}
			log.info(" Execution Completed ");
		} catch (Exception e) {
			log.info(" Exception in pageSourceData "+e.getMessage());
		}
		return responseCodes;
	}

	public static int countSymbol(String sourceType) {
		int charCount = 0;
		for (char ch : sourceType.toCharArray()) {
			if (ch == ',') {
				charCount++;
			}
		}
		return charCount + 1;

	}
}
