package com.rona.crudapi.dto;

import java.util.List;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DataEntity {
	List results;
	String message;
	int status;
}
