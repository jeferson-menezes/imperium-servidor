package com.zionflame.imperiumserver.controller.dto;

import lombok.Getter;

@Getter
public class MensagemDto {

	private String message;

	public MensagemDto(String message) {
		this.message = message;
	}

}
