package com.zionflame.imperiumserver.controller.dto;

import org.springframework.data.domain.Page;

import com.zionflame.imperiumserver.model.Receita;

import lombok.Getter;

@Getter
public class ReceitaDetalhesDto extends TransacaoDto {

	private static final long serialVersionUID = -4372368838002104992L;

	private String contaNome;
	private Long categoriaId;
	private Long contaId;

	public ReceitaDetalhesDto(Receita receita) {
		super(receita);
		contaNome = receita.getConta().getNome();
		categoriaId = receita.getCategoria().getId();
		contaId = receita.getConta().getId();
	}

	public static Page<ReceitaDto> converter(Page<Receita> receitas) {
		return receitas.map(ReceitaDto::new);
	}

}
