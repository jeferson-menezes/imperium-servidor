package com.zionflame.imperiumserver.controller.dto;

import java.util.List;
import java.util.stream.Collectors;

import com.zionflame.imperiumserver.model.Setor;

import lombok.Getter;

@Getter
public class SetorDto {

	private Long id;
	private String nome;

	public SetorDto(Setor setor) {
		id = setor.getId();
		nome = setor.getNome();
	}

	public static List<SetorDto> converter(List<Setor> setores) {
		return setores.stream().map(SetorDto::new).collect(Collectors.toList());
	}

}
