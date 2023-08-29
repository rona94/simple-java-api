package com.rona.crudapi.dto;

public interface UserEntity {
	int getId();
	String getEmail();
	String getPassword();
	String getFirstname();
	String getLastname();
	int getAge();
	String getAddress();
	String getContact();
	int getRole_id();
	String getRole();
}
