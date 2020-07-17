package com.zionflame.imperiumserver.controller.dto;

import org.springframework.data.domain.Page;

import com.zionflame.imperiumserver.model.Receita;

import lombok.Getter;

@Getter
public class ReceitaDetalhesDto extends TransacaoDto {

	private static final long serialVersionUID = -4372368838002104992L;

	private String contaNome;
	private boolean concluida;
	private CategoriaDto categoria;
	private ContaDto conta;

	public ReceitaDetalhesDto(Receita receita) {
		super(receita);
		concluida = receita.isConcluida();
		contaNome = receita.getConta().getNome();
		categoria = new CategoriaDto(receita.getCategoria());
		conta = new ContaDto(receita.getConta());
	}

	public static Page<ReceitaDto> converter(Page<Receita> receitas) {
		return receitas.map(ReceitaDto::new);
	}

}
