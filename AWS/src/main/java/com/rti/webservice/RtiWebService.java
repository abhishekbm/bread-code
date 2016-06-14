package com.rti.webservice;


import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/college")

public interface RtiWebService {

	String DRIVE_NAME="DriveName";
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/getAllStudents")
	public Response getStudentList()throws Exception;
	
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/insertStudent")
	public Response insertStudent(String Payload)throws Exception;

}
