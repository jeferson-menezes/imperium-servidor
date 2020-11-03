package com.zionflame.imperiumserver.controller.dto;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;

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

	public static Page<DespesaDto> converter(Page<Despesa> despesas) {
		return despesas.map(DespesaDto::new);
	}
	
	public static List<DespesaDto> converter(List<Despesa> despesas) {
		return despesas.stream().map(DespesaDto::new).collect(Collectors.toList());
	}

}
