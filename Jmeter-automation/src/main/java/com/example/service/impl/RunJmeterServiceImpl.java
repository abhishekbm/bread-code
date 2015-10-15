package com.example.service.impl;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;

import javax.ws.rs.core.Response;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.apache.jmeter.engine.StandardJMeterEngine;
import org.apache.jmeter.reporters.ResultCollector;
import org.apache.jmeter.reporters.Summariser;
import org.apache.jmeter.save.SaveService;
import org.apache.jmeter.util.JMeterUtils;
import org.apache.jorphan.collections.HashTree;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.Utils.PropertyFileLoader;


@Service("JmeterService")
public class RunJmeterServiceImpl {
	
	@Autowired
	PropertyFileLoader fileLoader;
	
	public Response runScript(final String scriptName ) throws Exception{

	
		
		StandardJMeterEngine jmeter = new StandardJMeterEngine();

		// Initialize Properties, logging, locale, etc.
			Response response = Response.ok().build();
			
		JMeterUtils.loadJMeterProperties(fileLoader.getJmeterBinLoc()+"/jmeter.properties");
		
		JMeterUtils.setJMeterHome(fileLoader.getJmeterHome());
		
		JMeterUtils.initLogging();// you can comment this line out to see extra
									// log messages of i.e. DEBUG level
		JMeterUtils.initLocale();

		// Initialize JMeter SaveService
		SaveService.loadProperties();

		// Load existing .jmx Test Plan
		
		FileInputStream in = new FileInputStream(getClass().getClassLoader().getResource(scriptName+".jmx").getFile());
		HashTree testPlanTree = SaveService.loadTree(in);
		in.close();
		/*Name Moderation*/
		// Run JMeter Test
		jmeter.configure(testPlanTree);

		Summariser summer = null;
		String summariserName = JMeterUtils.getPropDefault("summariser.name", "summary");
		if (summariserName.length() > 0) {
			summer = new Summariser(summariserName);
		}

		String logFile = fileLoader.getStoreJTLLoc()+scriptName+".jtl";
		ResultCollector logger = new ResultCollector(summer);
		logger.setFilename(logFile);
		testPlanTree.add(testPlanTree.getArray()[0], logger);

		jmeter.run();

		try {
			TransformerFactory tFactory = TransformerFactory.newInstance();
			Source xslDoc = new StreamSource(fileLoader.getXSLLocation());
			Source xmlDoc = new StreamSource(fileLoader.getStoreJTLLoc()+scriptName+".jtl");
			String outputFileName = fileLoader.getStoreHTMLGeneratedLoc()+scriptName+".html";
			OutputStream htmlFile = new FileOutputStream(outputFileName);
			Transformer transformer = tFactory.newTransformer(xslDoc);
			transformer.transform(xmlDoc, new StreamResult(htmlFile));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return response;
	}

	public void setFileLoader(final PropertyFileLoader fileLoader) {
		this.fileLoader = fileLoader;
	}
	
	/*public static void main(String args[]){
		try{
		runScript("Name Moderation");
	}
		catch(Exception ex )
		{
			
		}
		finally{
	}
		}*/
	
}
