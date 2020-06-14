package com.zionflame.imperiumserver.controller.dto;

import com.zionflame.imperiumserver.model.Receita;

import lombok.Getter;

@Getter
public class ReceitaDto extends TransacaoDto {

	private static final long serialVersionUID = -4372368838002104992L;

	private boolean concluida;
	private String contaNome;
	private String categoriaNome;

	public ReceitaDto(Receita receita) {
		super(receita);
		concluida = receita.isConcluida();
		contaNome = receita.getConta().getNome();
		categoriaNome = receita.getCategoria().getNome();
	}

}
