package com.rona.crudapi.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Class {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column
	private int teacher_id;
	
	@Column
	private String name;
	
	@Column
	private int year_from;
	
	@Column
	private int year_to;
}
