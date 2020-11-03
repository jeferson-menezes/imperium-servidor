package com.zionflame.imperiumserver.controller.dto;

import com.zionflame.imperiumserver.model.CompraCartao;

import lombok.Getter;

@Getter
public class CompraCartaoDto extends TransacaoDto {

	private static final long serialVersionUID = 160944720054412497L;

	public CompraCartaoDto(CompraCartao compraCartao) {
		super(compraCartao);
	}

}
