package com.zionflame.imperiumserver.controller.dto;

import com.zionflame.imperiumserver.model.CompraCartao;

import lombok.Getter;

@Getter
public class CompraCartaoDto extends TransacaoDto {

	public CompraCartaoDto(CompraCartao compraCartao) {
		super(compraCartao);
	}

}
