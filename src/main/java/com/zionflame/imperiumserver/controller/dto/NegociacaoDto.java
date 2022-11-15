package com.zionflame.imperiumserver.controller.dto;

import java.math.BigDecimal;

import com.zionflame.imperiumserver.model.Negociacao;
import com.zionflame.imperiumserver.model.enums.Movimentacao;

import lombok.Getter;

@Getter
public class NegociacaoDto {

	private Long id;
	private BigDecimal valorTotal;
	private Integer quantidade;
	private BigDecimal preco;
	private Movimentacao movimentacao;

	public NegociacaoDto(Negociacao negociacao) {
		id = negociacao.getId();
		preco = negociacao.getPreco();
		movimentacao = negociacao.getMovimentacao();
		quantidade = negociacao.getQuantidade();
		valorTotal = negociacao.getValorTotal();
	}

}
