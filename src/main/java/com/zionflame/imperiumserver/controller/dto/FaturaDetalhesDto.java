package com.zionflame.imperiumserver.controller.dto;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.zionflame.imperiumserver.model.Fatura;
import com.zionflame.imperiumserver.model.enums.StatusFatura;

import lombok.Getter;

@Getter
public class FaturaDetalhesDto {

	private Long id;
	private int mes;
	private int ano;
	private String mesExtenso;
	private StatusFatura status;
	private LocalDate vencimento;
	private LocalDate fechamento;
	private List<ItemFaturaDto> itens = new ArrayList<>();

	public FaturaDetalhesDto(Fatura fatura) {
		id = fatura.getId();
		mes = fatura.getMes();
		ano = fatura.getAno();
		mesExtenso = fatura.getMesExtenso();
		status = fatura.getStatus();
		vencimento = fatura.getVencimento();
		fechamento = fatura.getFechamento();
		itens = ItemFaturaDto.converter(fatura.getItens());
	}

}
