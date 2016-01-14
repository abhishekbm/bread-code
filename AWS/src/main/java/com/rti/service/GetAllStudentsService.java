package com.rti.service;

import net.sf.json.JSONObject;

public interface GetAllStudentsService {

	JSONObject getAllStudents();
	
	void insertStudent(JSONObject json);
}
