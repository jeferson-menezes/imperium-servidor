package com.zionflame.imperiumserver.controller.dto;

import org.springframework.data.domain.Page;

import com.zionflame.imperiumserver.model.Despesa;

import lombok.Getter;

@Getter
public class DespesaDetalhesDto extends TransacaoDto {

	private static final long serialVersionUID = 2221769647092369071L;

	private String contaNome;
	private boolean concluida;
	private Long categoriaId;
	private Long contaId;

	public DespesaDetalhesDto(Despesa despesa) {
		super(despesa);
		concluida = despesa.isConcluida();
		contaNome = despesa.getConta().getNome();
		categoriaId = despesa.getCategoria().getId();
		contaId = despesa.getConta().getId();
	}

	public static Page<DespesaDetalhesDto> converter(Page<Despesa> despesas) {
		return despesas.map(DespesaDetalhesDto::new);
	}

}
