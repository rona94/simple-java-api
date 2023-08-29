package com.rona.crudapi.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class SubjectSchedule {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column
	private int teacher_id;
	
	@Column
	private int subject_id;
	
	@Column
	private String time_from;
	
	@Column
	private String time_to;
	
	@Column
	private String day;
}
