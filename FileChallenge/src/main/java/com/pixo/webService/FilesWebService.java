package com.pixo.webService;


import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.stereotype.Component;

@Path("/file")

public interface FilesWebService {

	String DRIVE_NAME="DriveName";
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/{"+DRIVE_NAME+"}/GenerateReports")
	public Response getFileList(@PathParam(DRIVE_NAME) String nameofDrive)throws Exception;
}
