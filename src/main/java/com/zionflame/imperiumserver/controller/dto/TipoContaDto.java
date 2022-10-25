package com.zionflame.imperiumserver.controller.dto;

import java.util.List;
import java.util.stream.Collectors;

import com.zionflame.imperiumserver.model.TipoConta;

import lombok.Getter;

@Getter
public class TipoContaDto {

	private Long id;
	private String nome;
	protected String cor;
	protected String icone;
	private String descricao;

	public TipoContaDto(TipoConta tipo) {
		id = tipo.getId();
		nome = tipo.getNome();
		cor = tipo.getCor();
		icone = tipo.getIcone();
		descricao = tipo.getDescricao();
	}

	public static List<TipoContaDto> converter(List<TipoConta> tipos) {
		return tipos.stream().map(TipoContaDto::new).collect(Collectors.toList());
	}

}
