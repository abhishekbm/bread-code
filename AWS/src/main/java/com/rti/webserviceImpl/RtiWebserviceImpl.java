package com.rti.webserviceImpl;

import javax.ws.rs.core.Response;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.rti.serviceImpl.GetAllStudentsServiceImpl;
import com.rti.webservice.RtiWebService;

@Component("rtiWebserviceImpl")
public class RtiWebserviceImpl implements RtiWebService {
	@Autowired
	private GetAllStudentsServiceImpl getAllStudentsServiceImpl;

	public Response getStudentList() throws Exception {
		JSONObject response = getAllStudentsServiceImpl.getAllStudents();
		return Response.ok(response.toString()).build();
	}
	public Response createAccount(String Payload) throws Exception {
		JSONObject json = JSONObject.fromObject(Payload);
		getAllStudentsServiceImpl.createAccount(json);
		return Response.ok(json.toString()).build();
	}
	public Response insertStudent(String Payload) throws Exception {
		JSONObject json = JSONObject.fromObject(Payload);
		getAllStudentsServiceImpl.insertStudent(json);
		return Response.ok().build();
	}
}
