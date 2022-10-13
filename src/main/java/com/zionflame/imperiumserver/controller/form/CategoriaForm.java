package com.zionflame.imperiumserver.controller.form;

import javax.validation.constraints.NotNull;

import com.zionflame.imperiumserver.model.Categoria;
import com.zionflame.imperiumserver.model.Visual;
import com.zionflame.imperiumserver.model.enums.NaturezaCategoria;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoriaForm extends Visual {

	private static final long serialVersionUID = -1496111152812982868L;

	@NotNull
	private String nome;
	@NotNull
	private String descricao;
	@NotNull
	private NaturezaCategoria natureza;
	
	private Boolean ativo;

	public Categoria converter() {
		return new Categoria(nome, descricao, natureza, ativo, cor, icone);
	}

}
