package com.rona.crudapi.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
	private String email;
	private String password;
	private String firstname;
	private String lastname;
	private int age;
	private String address;
	private String contact;
}
