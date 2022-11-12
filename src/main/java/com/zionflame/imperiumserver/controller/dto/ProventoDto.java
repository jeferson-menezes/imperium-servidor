package com.zionflame.imperiumserver.controller.dto;

import com.zionflame.imperiumserver.model.Provento;

import lombok.Getter;

@Getter
public class ProventoDto {

	private Long id;

	public ProventoDto(Provento provento) {
		id = provento.getId();
	}

}
