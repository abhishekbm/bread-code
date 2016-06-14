package com.rti.daoImpl;

import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONObject;

import org.springframework.orm.jpa.JpaTemplate;
import org.springframework.transaction.annotation.Transactional;

import com.rti.dao.GetStudentsDAO;
import com.rti.entities.Student;

public class GetStudentsDAOImpl implements GetStudentsDAO {

	private JpaTemplate jpatemplate;
	
	


	public List<JSONObject> getStudents() {
		List<JSONObject> list1 = new ArrayList<JSONObject>();
		List<Student> list = new ArrayList<Student>();
		list =jpatemplate.findByNamedQuery("Student.findAll");
	
	
		for (int i = 0; i < list.size(); i++) {
			JSONObject Log = new JSONObject();
			Student student = list.get(i);
			Log.put("studentid", student.getStudentId());
			Log.put("semester", student.getSemester());
			Log.put("name", student.getName());
			list1.add(Log);

		}
		return list1;
	}
@Transactional
public void insertStudent(Student student){
	jpatemplate.persist(student);
}

public JpaTemplate getJpatemplate() {
	return jpatemplate;
}

public void setJpatemplate(JpaTemplate jpatemplate) {
	this.jpatemplate = jpatemplate;
}	
	
}
