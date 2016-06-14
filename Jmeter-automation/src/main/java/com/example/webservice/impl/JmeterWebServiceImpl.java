package com.example.webservice.impl;

import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.service.impl.RunJmeterServiceImpl;
import com.example.webservice.JMeterWebservice;


@Component("lapsWebService")
public class JmeterWebServiceImpl implements JMeterWebservice {
	
	@Autowired
	private RunJmeterServiceImpl impl;
	
	public Response scriptWebService(String nameofScript) throws Exception {
		
		Response response = impl.runScript(nameofScript);
		return response;
	
	}

	public void setImpl(final RunJmeterServiceImpl impl) {
		this.impl = impl;
	}
	
	
}
