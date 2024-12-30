package com.example.restapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.restapi.entity.student;

public interface studentRepository extends JpaRepository<student,Integer> {

}
