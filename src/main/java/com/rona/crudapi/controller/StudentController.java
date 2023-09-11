package com.rona.crudapi.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.rona.crudapi.config.JwtService;
import com.rona.crudapi.dto.ClassEntity;
import com.rona.crudapi.dto.ClassScheduleEntity;
import com.rona.crudapi.dto.DataEntity;
import com.rona.crudapi.dto.StudentGradeEntity;
import com.rona.crudapi.dto.UserEntity;
import com.rona.crudapi.repo.UserRepo;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/student")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "Authorization")
public class StudentController {
	private final JwtService jwtService;
	
	@Autowired
	private UserRepo userRepo;
	
	// GET student's grade
	@GetMapping(value = "/grade")
	public ResponseEntity<DataEntity> getStudentGrade(
			@NonNull HttpServletRequest request
	) {
		try {
			final String token = request.getHeader("Authorization");
			final String user = getUser(token);
			
			UserEntity userInfo = userRepo.getUserInfo(user);			
			
			DataEntity result = DataEntity.builder()
										.results(userRepo.getStudentGrade(userInfo.getId()))
										.status(200)
										.message("Success")
										.build();
			return ResponseEntity.ok(result);
			
		}
		catch(Exception e) {
			DataEntity result = DataEntity.builder()
									.results(null)
									.status(500)
									.message("Error")
									.build();
			return ResponseEntity.ok(result);
		}
	}	
	
	private String getUser(String token) {
		String jwt = token.substring(7);
		String user = jwtService.extractUsername(jwt);
		return user;
	}
	
///////////////////////////////////////////
///////////////////////////////////////////
///////////////////////////////////////////
		
//	// GET student's class info
//	@GetMapping(value = "/class")
//	public ResponseEntity<ClassEntity> getStudentClass(@RequestParam("user") String user) {
//		return ResponseEntity.ok(userRepo.getStudentClass(1));
//	}
	
	// GET student's schedule
	@GetMapping(value = "/schedule/{id}")
	public ResponseEntity<List<ClassScheduleEntity>> getStudentSchedule(@PathVariable int id) {
		return ResponseEntity.ok(userRepo.getClassSchedule(id));
	}
}
