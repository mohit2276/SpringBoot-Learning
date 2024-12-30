package com.example.restapi.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.restapi.entity.student;
import com.example.restapi.repository.studentRepository;

@RestController
public class studentController {
	//get all student are in table 
	@Autowired
	studentRepository repo;
	@GetMapping("/students")
	public List<student> getAllStudents(){
		List<student> students= repo.findAll();
		return students;
	}
	
	@GetMapping("/students/{id}")
	public student getStudent(@PathVariable int id) {
		student studen =repo.findById(id).get();
		return studen;
		
	}
	
	@PostMapping("student/add")
	public void createStudent(@RequestBody student studen) {
		repo.save(studen);
	}
	
	@PutMapping("/student/update/{id}")
	public student updateStudent(@PathVariable int id) {
		student stu=repo.findById(id).get();
		stu.setName("pk");
		stu.setBranch("jee");
		repo.save(stu);
		return stu;
	}
	
	@DeleteMapping("/student/delete/{id}")
	public void deleteStudent(@PathVariable int id) {
		student st=repo.findById(id).get();
		repo.delete(st);
	}

}
