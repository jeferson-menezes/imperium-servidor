package com.zionflame.imperiumserver.controller.dto;

import lombok.Getter;

@Getter
public class MensagemDto {

	private String string;

	public MensagemDto(String string) {
		this.string = string;
	}

}
