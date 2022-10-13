package com.zionflame.imperiumserver.controller.dto;

import java.math.BigDecimal;

import com.zionflame.imperiumserver.model.Conta;
import com.zionflame.imperiumserver.model.TipoConta;

import lombok.Getter;

@Getter
public class ContaDetalhesDto {

	private Long id;
	private String nome;
	private BigDecimal saldo;
	private String descricao;
	private boolean incluiSoma;
	private boolean ativo;
	private TipoConta tipo;

	public ContaDetalhesDto(Conta conta) {
		id = conta.getId();
		nome = conta.getNome();
		descricao = conta.getDescricao();
		saldo = conta.getSaldo();
		ativo = conta.getAtivo();
		incluiSoma = conta.getIncluiSoma();
		tipo = conta.getTipo();
	}

}
