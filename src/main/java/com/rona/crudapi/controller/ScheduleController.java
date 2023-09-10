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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.rona.crudapi.config.JwtService;
import com.rona.crudapi.dto.ClassScheduleEntity;
import com.rona.crudapi.dto.UserEntity;
import com.rona.crudapi.repo.UserRepo;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "Authorization")
public class ScheduleController {
	private final JwtService jwtService;
	
	@Autowired
	private UserRepo userRepo;
		
	// GET student's class info
	@GetMapping(value = "/schedule")
	public ResponseEntity<List<ClassScheduleEntity>> getStudentSchedule(
			@NonNull HttpServletRequest request,
			@RequestParam(name = "sort", defaultValue = "id") String sort, 
			@RequestParam(name = "order", defaultValue = "1") int order,
			@RequestParam(name = "page", defaultValue = "0") int page,
			@RequestParam(name = "page_size", defaultValue = "25") int pageSize
	) {
		try {
			final String token = request.getHeader("Authorization");
			final String user = getUser(token);
			
			UserEntity userInfo = userRepo.getUserInfo(user);
			List<ClassScheduleEntity> data = null;
			
			String newSort = sort;
			var newOrder = order == 1 ? Sort.Direction.ASC : Sort.Direction.DESC;
			
			Sort sort1 = Sort.by(newOrder, newSort);
			Pageable paging = PageRequest.of(page, pageSize, sort1);
			
			if(userInfo.getRole_id() == 3) { // do if student
				data = userRepo.getStudentSchedule(userInfo.getId(), paging);
			}
			else if(userInfo.getRole_id() == 2) { // do if teacher
				data = userRepo.getFacultySchedule(userInfo.getId(), paging);
			}
			
			return ResponseEntity.ok(data);
		}
		catch(Exception e) {
			return ResponseEntity.ok(null);
		}
	}
	
	private String getUser(String token) {
		String jwt = token.substring(7);
		String user = jwtService.extractUsername(jwt);
		return user;
	}
}
