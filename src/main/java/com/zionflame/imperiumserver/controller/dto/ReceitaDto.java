package com.zionflame.imperiumserver.controller.dto;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;

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

	public static Page<ReceitaDto> converter(Page<Receita> receitas) {
		return receitas.map(ReceitaDto::new);
	}
	

	public static List<ReceitaDto> converter(List<Receita> receitas) {
		return receitas.stream().map(ReceitaDto::new).collect(Collectors.toList());
	}

}
