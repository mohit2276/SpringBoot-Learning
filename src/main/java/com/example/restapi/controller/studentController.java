package com.example.restapi.controller;

import java.util.List;
import java.util.Optional;

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

	
	 @Autowired
	    private StudentService studentService;

	    @GetMapping("/students")
	    public ResponseEntity<List<student>> getAllStudents() {
	        return ResponseEntity.ok(studentService.getAllStudents());
	    }

	    
	    @GetMapping("/students/{id}")
	    public ResponseEntity<?> getStudentById(@PathVariable int id) {
	        Optional<student> studentOptional = studentService.getStudentById(id);
	        if (studentOptional.isPresent()) {
	            return ResponseEntity.ok(studentOptional.get());
	        } else {
	            return ResponseEntity.status(404).body("Student with ID " + id + " not found.");
	        }
	    }

   

	    @PostMapping("/studentscreate")
	    public ResponseEntity<student> createStudent(@RequestBody student studen) {
	        return ResponseEntity.ok(studentService.createStudent(studen));
	    }

	    @PutMapping("/students/{id}")
	    public ResponseEntity<?> updateStudent(@PathVariable int id, @RequestBody student updatedStudent) {
	        try {
	            return ResponseEntity.ok(studentService.updateStudent(id, updatedStudent));
	        } catch (RuntimeException e) {
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
