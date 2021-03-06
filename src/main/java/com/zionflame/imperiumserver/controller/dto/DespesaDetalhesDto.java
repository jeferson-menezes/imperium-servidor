package com.zionflame.imperiumserver.controller.dto;

import org.springframework.data.domain.Page;

import com.zionflame.imperiumserver.model.Despesa;

import lombok.Getter;

@Getter
public class DespesaDetalhesDto extends TransacaoDto {

	private static final long serialVersionUID = 2221769647092369071L;

	private String contaNome;
	private boolean concluida;
	private CategoriaDto categoria;
	private ContaDto conta;

	public DespesaDetalhesDto(Despesa despesa) {
		super(despesa);
		concluida = despesa.isConcluida();
		contaNome = despesa.getConta().getNome();
		categoria = new CategoriaDto(despesa.getCategoria());
		conta = new ContaDto(despesa.getConta());
	}

	public static Page<DespesaDetalhesDto> converter(Page<Despesa> despesas) {
		return despesas.map(DespesaDetalhesDto::new);
	}

}
