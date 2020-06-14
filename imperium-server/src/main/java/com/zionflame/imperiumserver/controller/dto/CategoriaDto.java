package com.zionflame.imperiumserver.controller.dto;

import java.util.List;
import java.util.stream.Collectors;

import com.zionflame.imperiumserver.model.Categoria;
import com.zionflame.imperiumserver.model.enums.NaturezaCategoria;

import lombok.Getter;

@Getter
public class CategoriaDto {

	private Long id;
	private String nome;
	private String descricao;
	private NaturezaCategoria natureza;

	public CategoriaDto(Categoria categoria) {
		id = categoria.getId();
		nome = categoria.getNome();
		descricao = categoria.getDescricao();
		natureza = categoria.getNatureza();
	}

	public static List<CategoriaDto> converter(List<Categoria> categorias) {
		return categorias.stream().map(CategoriaDto::new).collect(Collectors.toList());
	}

}
