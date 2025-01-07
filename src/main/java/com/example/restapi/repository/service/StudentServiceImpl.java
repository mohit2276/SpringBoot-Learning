package com.example.restapi.repository.service;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.restapi.entity.student;
import com.example.restapi.repository.studentRepository;

@Service
public class StudentServiceImpl implements StudentService{
	
	private static final Logger logger = LoggerFactory.getLogger(StudentServiceImpl.class);
	
	@Autowired
    private studentRepository repo;

    @Override
    public List<student> getAllStudents() {
    	logger.info("Fetching all students.");
        return repo.findAll();
    }

    @Override
    public Optional<student> getStudentById(int id) {
    	logger.debug("Finding by id ");
        return repo.findById(id);
    }

    @Override
    public student createStudent(student studen) {
        return repo.save(studen);
    }

    @Override
    public student updateStudent(int id, student updatedStudent) {
        Optional<student> optionalStudent = repo.findById(id);
        if (optionalStudent.isPresent()) {
            student stu = optionalStudent.get();
            stu.setName(updatedStudent.getName());
            stu.setBranch(updatedStudent.getBranch());
            stu.setPercentage(updatedStudent.getPercentage());
            return repo.save(stu);
        }
        throw new RuntimeException("Student with ID " + id + " not found.");
    }

    @Override
    public void deleteStudent(int id) {
        Optional<student> optionalStudent = repo.findById(id);
        if (optionalStudent.isPresent()) {
            repo.delete(optionalStudent.get());
        } else {
        	logger.error("student id not found");
            throw new RuntimeException("Student with ID " + id + " not found.");
        }
    }

}
