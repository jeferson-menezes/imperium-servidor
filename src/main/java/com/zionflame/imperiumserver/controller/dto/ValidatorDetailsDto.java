package com.zionflame.imperiumserver.controller.dto;

import lombok.Getter;

@Getter
public class ValidatorDetailsDto {

	private final String message;
	private final String field;

	public ValidatorDetailsDto(String message, String field) {

		this.message = message;
		this.field = field;
	}

}
