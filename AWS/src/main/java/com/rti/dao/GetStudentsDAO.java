package com.rti.dao;

import java.util.List;

import net.sf.json.JSONObject;

import com.rti.entities.Student;

public interface GetStudentsDAO {

	List<JSONObject>getStudents();
	
	void insertStudent(Student student);
}
