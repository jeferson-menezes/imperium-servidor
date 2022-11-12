package com.zionflame.imperiumserver.controller.dto;

import java.util.List;
import java.util.stream.Collectors;

import com.zionflame.imperiumserver.model.TipoAtivo;
import com.zionflame.imperiumserver.model.enums.Renda;

import lombok.Getter;

@Getter
public class TipoAtivoDto {

	private final Long id;
	private final String nome;
	private final Renda renda;

	public TipoAtivoDto(TipoAtivo tipo) {
		id = tipo.getId();
		nome = tipo.getNome();
		renda = tipo.getRenda();
	}

	public static List<TipoAtivoDto> converter(List<TipoAtivo> tipos) {
		return tipos.stream().map(TipoAtivoDto::new).collect(Collectors.toList());
	}

}
