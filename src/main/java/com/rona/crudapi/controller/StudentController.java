package com.rona.crudapi.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rona.crudapi.dto.ClassEntity;
import com.rona.crudapi.dto.ClassScheduleEntity;
import com.rona.crudapi.dto.StudentGradeEntity;
import com.rona.crudapi.dto.UserEntity;
import com.rona.crudapi.models.User;
import com.rona.crudapi.repo.UserRepo;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/student")
@RequiredArgsConstructor
public class StudentController {
	@Autowired
	private UserRepo userRepo;
		
	// GET student's class info
	@GetMapping(value = "/class/{id}")
	public ResponseEntity<ClassEntity> getStudentClass(@PathVariable int id) {
		return ResponseEntity.ok(userRepo.getStudentClass(id));
	}
	
	// GET student's schedule
	@GetMapping(value = "/schedule/{id}")
	public ResponseEntity<List<ClassScheduleEntity>> getClassSchedule(@PathVariable int id) {
		return ResponseEntity.ok(userRepo.getClassSchedule(id));
	}
		
	// GET student's grade
	@GetMapping(value = "/grade/{id}")
	public ResponseEntity<List<StudentGradeEntity>> getStudentGrade(@PathVariable int id) {
		return ResponseEntity.ok(userRepo.getStudentGrade(id));
	}
}
