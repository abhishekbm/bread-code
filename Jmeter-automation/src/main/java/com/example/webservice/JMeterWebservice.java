package com.example.webservice;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/laps")
public interface JMeterWebservice {
	
	String SCRIPT_NAME="ScriptName";
	

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/{"+SCRIPT_NAME+"}/GenerateReports")
	Response scriptWebService(@PathParam(SCRIPT_NAME) String nameofScript) throws Exception;
}
