package com.rti.entities;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@javax.persistence.Entity
@Table(name="STUDENT_DATA")
@Access(AccessType.FIELD)

@NamedQueries({

	@NamedQuery(name = "Student.findAll",
			query = "select n from Student n")
		
	}		
)
public class Student {

@Id	
@Column(name="stu_id")	
private String studentId;

@Column(name="sem")	
private String semester;

@Column(name="name")	
private String name;

/**
 * @return the studentId
 */
public String getStudentId() {
	return studentId;
}

/**
 * @param studentId the studentId to set
 */
public void setStudentId(String studentId) {
	this.studentId = studentId;
}

/**
 * @return the semester
 */
public String getSemester() {
	return semester;
}

/**
 * @param semester the semester to set
 */
public void setSemester(String semester) {
	this.semester = semester;
}

/**
 * @return the name
 */
public String getName() {
	return name;
}

/**
 * @param name the name to set
 */
public void setName(String name) {
	this.name = name;
}
}
