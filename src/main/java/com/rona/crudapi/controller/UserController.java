package com.rona.crudapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.rona.crudapi.dto.DataEntity;
import com.rona.crudapi.dto.UserEntity;
import com.rona.crudapi.models.User;
import com.rona.crudapi.repo.UserRepo;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
@CrossOrigin()
public class UserController {
	@Autowired
	private UserRepo userRepo;

	// GET user info
	@GetMapping(value = "/info")
	public ResponseEntity<UserEntity> getUserLogin(@RequestParam("user") String user) {
		return ResponseEntity.ok( userRepo.getUserInfo(user) );
	}
	
	// POST update user info
	@PostMapping(value = "/update")
	public ResponseEntity<DataEntity> updateUserInfo(@RequestBody User user) {
		try {
			User updatedUser = (User) userRepo.findByEmail(user.getEmail());
			
			updatedUser.setFirstname(user.getFirstname());
			updatedUser.setLastname(user.getLastname());
			updatedUser.setAge(user.getAge());
			updatedUser.setAddress(user.getAddress());
			updatedUser.setContact(user.getContact());
			userRepo.save(updatedUser);
			
			DataEntity data = DataEntity.builder()
					.results(null)
					.status(200)
					.message("Info Updated...")
					.build();
			return ResponseEntity.ok( data );
		}
		catch(Exception e) {
			DataEntity data = DataEntity.builder()
					.results(null)
					.status(500)
					.message("Error")
					.build();
			return ResponseEntity.ok( data );
		}
	}
	
//	POST save user student
//	@PostMapping(value = "/add_student")
//	public String saveUser(@RequestBody User user) {
//		try {
//			userRepo.save(user);
//			return "Student Saved...";
//		}
//		catch(Exception e) {
//			return "Error";
//		}
//	}
	
	// POST delete user
//	@PostMapping(value = "/delete_user/{id}")
//	public String deleteUser(@PathVariable int id) {
//		try {
//			User deleteUser = (User) userRepo.findAllById(id);
//			userRepo.delete(deleteUser);
//			
//			return "User Deleted...";
//		}
//		catch(Exception e) {
//			return "Error";
//		}
//	}
	
//	// POST update user email
//	@PostMapping(value = "/update_email/{id}")
//	public String updateUserEmail(@PathVariable int id, @RequestBody User user) {
//		try {
//			User updatedUser = (User) userRepo.findAllById(id);
//			
//			updatedUser.setEmail(user.getEmail());
//			userRepo.save(updatedUser);
//			
//			return "Email Updated...";
//		}
//		catch(Exception e) {
//			return "Error";
//		}
//	}
}
