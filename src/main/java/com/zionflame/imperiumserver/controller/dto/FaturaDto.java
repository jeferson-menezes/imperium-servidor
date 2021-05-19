package com.zionflame.imperiumserver.controller.dto;

import java.util.List;
import java.util.stream.Collectors;

import com.zionflame.imperiumserver.model.Fatura;
import com.zionflame.imperiumserver.model.enums.StatusFatura;

import lombok.Getter;

@Getter
public class FaturaDto {

	private Long id;
	private StatusFatura status;
	private int mes;
	private String mesExtenso;

	public FaturaDto(Fatura fatura) {

		id = fatura.getId();
		status = fatura.getStatus();
		mes = fatura.getMes();
		mesExtenso = fatura.getMesExtenso();
	}

	public static List<FaturaDto> converter(List<Fatura> faturas) {
		return faturas.stream().map(FaturaDto::new).collect(Collectors.toList());
	}
}
