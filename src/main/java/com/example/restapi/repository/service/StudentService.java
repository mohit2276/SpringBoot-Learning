package com.example.restapi.repository.service;

import java.util.List;
import java.util.Optional;


import org.springframework.stereotype.Service;

import com.example.restapi.entity.student;



@Service
public interface StudentService {
	
	 List<student> getAllStudents();
	    Optional<student> getStudentById(int id);
	    student createStudent(student studen);
	    student updateStudent(int id, student updatedStudent);
	    void deleteStudent(int id);
}
