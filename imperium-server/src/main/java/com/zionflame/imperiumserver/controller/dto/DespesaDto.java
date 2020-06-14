package com.zionflame.imperiumserver.controller.dto;

import com.zionflame.imperiumserver.model.Despesa;

import lombok.Getter;

@Getter
public class DespesaDto extends TransacaoDto {

	private static final long serialVersionUID = 2221769647092369071L;

	private String categoriaNome;
	private String contaNome;

	private boolean concluida;

	public DespesaDto(Despesa despesa) {
		super(despesa);
		concluida = despesa.isConcluida();
		contaNome = despesa.getConta().getNome();
		categoriaNome = despesa.getCategoria().getNome();
	}

}
