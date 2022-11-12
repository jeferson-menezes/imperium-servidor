package com.zionflame.imperiumserver.controller.dto;

import java.util.List;
import java.util.stream.Collectors;

import com.zionflame.imperiumserver.model.Ativo;

import lombok.Getter;

@Getter
public class AtivoDto {

	private Long id;
	private String nome;
	private String codigo;
	private Long setorId;
	private String setorNome;
	private Long imagemId;
	private String tipoAtivoNome;
	private Long tipoAtivoId;

	public AtivoDto(Ativo ativo) {
		id = ativo.getId();
		nome = ativo.getNome();
		codigo = ativo.getCodigo();
		setorId = ativo.getSetor().getId();
		setorNome = ativo.getSetor().getNome();
		tipoAtivoId = ativo.getTipoAtivo().getId();
		tipoAtivoNome = ativo.getTipoAtivo().getNome();
		imagemId = ativo.getImagem() != null ? ativo.getImagem().getId() : null;
	}

	public static Object converter(List<Ativo> ativos) {
		return ativos.stream().map(AtivoDto::new).collect(Collectors.toList());
	}

}
