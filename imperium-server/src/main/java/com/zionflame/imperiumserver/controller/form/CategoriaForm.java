package com.zionflame.imperiumserver.controller.form;

import com.zionflame.imperiumserver.model.Categoria;
import com.zionflame.imperiumserver.model.enums.NaturezaCategoria;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoriaForm {

	private boolean ativo;
	private NaturezaCategoria natureza;
	private String descricao;
	private String nome;

	public Categoria converter() {
		return new Categoria(nome, descricao, natureza, ativo);
	}

}
