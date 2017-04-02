package com.rti.serviceImpl;

import java.sql.Timestamp;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rti.daoImpl.GetStudentsDAOImpl;
import com.rti.entities.Account;
import com.rti.entities.Student;
import com.rti.service.GetAllStudentsService;

@Service("getAllStudentsServiceImpl")
public class GetAllStudentsServiceImpl implements GetAllStudentsService {
	@Autowired
	private GetStudentsDAOImpl getStudentsDAOImpl;

	public JSONObject getAllStudents() {
		List<JSONObject> list = getStudentsDAOImpl.getStudents();
		JSONArray array = new JSONArray();
		for (int i = 0; i < list.size(); i++) {
			array.add(list.get(i));
		}
		JSONObject json = new JSONObject();
		json.put("students", array);
		return json;
	}
	public void createAccount(JSONObject json) {
		Account account = new Account();
		account.setCreatedBy("user");
		account.setEmailAddress(json.getString("email"));
		account.setPassword(json.getString("pass"));
		account.setCreationDate(new Timestamp(System.currentTimeMillis()));
		account.setUpdateReason("Account creation");
		getStudentsDAOImpl.createAccount(account);
	}
	public void insertStudent(JSONObject json) {
		Student student = new Student();
		student.setName(json.getString("name"));
		student.setSemester(json.getString("sem"));
		student.setStudentId(json.getString("studentId"));
		getStudentsDAOImpl.insertStudent(student);
		
	}

}
