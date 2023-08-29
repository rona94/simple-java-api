package com.rona.crudapi.auth;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.rona.crudapi.config.JwtService;
import com.rona.crudapi.controller.Role;
import com.rona.crudapi.dto.UserEntity;
import com.rona.crudapi.models.User;
import com.rona.crudapi.repo.UserRepo;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {

	private final UserRepo repository;
	private final PasswordEncoder passwordEncoder;
	private final JwtService jwtService;
	private final AuthenticationManager authManager;
	private final BCryptPasswordEncoder encoded;
	
	public AuthResponse register(RegisterRequest request) {
		var user = User.builder()
				.email(request.getEmail())
				.password(passwordEncoder.encode(request.getPassword()))
				.firstname(request.getFirstname())
				.lastname(request.getLastname())
				.age(request.getAge())
				.address(request.getAddress())
				.contact(request.getContact())
				.role(Role.STUDENT)
				.build();
			
		repository.save(user);
		
		var jwtToken = jwtService.generateToken(user);
		return AuthResponse.builder()
				.token(jwtToken)
				.build();
	}
	
	public AuthResponse login(AuthRequest request) {
		var user = repository.findByEmail(request.getEmail());
		boolean isPasswordMatch = encoded.matches(request.getPassword(), user.getPassword());
		
		if(isPasswordMatch) {			
			var jwtToken = jwtService.generateToken(user);
			return AuthResponse.builder()
				.token(jwtToken)
				.build();
		}
		return null;
	}

//	public AuthResponse authenticate(AuthRequest request) {
////		authManager.authenticate(
////			new UsernamePasswordAuthenticationToken(
////					request.getEmail(),
////					request.getPassword()
////			)
////		);
//		var user = repository.findByEmail(request.getEmail());
//		var jwtToken = jwtService.generateToken(user);
//		
//		return AuthResponse.builder()
//				.token(jwtToken)
//				.build();
//	}
}
