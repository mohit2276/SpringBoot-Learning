package com.example.restapi.controller;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.restapi.entity.student;
import com.example.restapi.repository.studentRepository;
import com.example.restapi.repository.service.StudentService;

@RestController
@RequestMapping("/api")
public class studentController {
	
	private static final Logger logger = LoggerFactory.getLogger(studentController.class);
	

	
	 @Autowired
	    private StudentService studentService;
	 
	 @GetMapping("/")
	 public String home() {
		 logger.info("Home endpoint accessed.");
		 return("<h1>Welcome</h1>");
	 }

	    @GetMapping("/students")
	    public ResponseEntity<List<student>> getAllStudents() {
	    	logger.debug("Fetching all students.");
	        return ResponseEntity.ok(studentService.getAllStudents());
	    }

	    
	    @GetMapping("/students/{id}")
	    public ResponseEntity<?> getStudentById(@PathVariable int id) {
	        Optional<student> studentOptional = studentService.getStudentById(id);
	        if (studentOptional.isPresent()) {
	        	logger.debug("student id found ");
	            return ResponseEntity.ok(studentOptional.get());
	        } else {
	        	logger.error("Student id not found");
	            return ResponseEntity.status(404).body("Student with ID " + id + " not found.");
	        }
	    }

   

	    @PostMapping("/studentscreate")
	    public ResponseEntity<student> createStudent(@RequestBody student studen) {
	    	logger.debug("student created successfully");
	        return ResponseEntity.ok(studentService.createStudent(studen));
	    }

	    @PutMapping("/students/{id}")
	    public ResponseEntity<?> updateStudent(@PathVariable int id, @RequestBody student updatedStudent) {
	        try {
	            return ResponseEntity.ok(studentService.updateStudent(id, updatedStudent));
	        } catch (RuntimeException e) {
	        	logger.error("student details  not updated ");
	            return ResponseEntity.status(404).body(e.getMessage());
	        }
	    }

	    @DeleteMapping("/students/{id}")
	    public ResponseEntity<?> deleteStudent(@PathVariable int id) {
	        try {
	            studentService.deleteStudent(id);
	            return ResponseEntity.ok("Student deleted successfully");
	        } catch (RuntimeException e) {
	            return ResponseEntity.status(404).body(e.getMessage());
	        }
	    }
}
