package com.example.Utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component("propertyUtils")
public class PropertyFileLoader {

	@Value("${JMeterHome.Location}")
	private String JmeterHome;
	
	@Value("${JMeter.bin.Location}")
	private String JmeterBinLoc;
	
	@Value("${JMeter.JTL.Location}")
	private String storeJTLLoc;
	
	@Value("${JMeter.HTML.Location}")
	private String storeHTMLGeneratedLoc; 
	
	@Value("${JMeter.XSL.Location}")
	private String XSLLocation;
	
	public String getJmeterHome() {
		return JmeterHome;
	}

	public void setJmeterHome(final String jmeterHome) {
		JmeterHome = jmeterHome;
	}

	public String getJmeterBinLoc() {
		return JmeterBinLoc;
	}

	public void setJmeterBinLoc(final String jmeterBinLoc) {
		JmeterBinLoc = jmeterBinLoc;
	}

	public String getStoreJTLLoc() {
		return storeJTLLoc;
	}

	public void setStoreJTLLoc(final String storeJTLLoc) {
		this.storeJTLLoc = storeJTLLoc;
	}

	public String getStoreHTMLGeneratedLoc() {
		return storeHTMLGeneratedLoc;
	}

	public void setStoreHTMLGeneratedLoc(String storeHTMLGeneratedLoc) {
		this.storeHTMLGeneratedLoc = storeHTMLGeneratedLoc;
	}

	public String getXSLLocation() {
		return XSLLocation;
	}

	public void setXSLLocation(final String xSLLocation) {
		XSLLocation = xSLLocation;
	}
	
	
}
