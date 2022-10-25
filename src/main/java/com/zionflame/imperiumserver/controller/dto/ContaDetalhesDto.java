package com.zionflame.imperiumserver.controller.dto;

import java.math.BigDecimal;

import com.zionflame.imperiumserver.model.Conta;

import lombok.Getter;

@Getter
public class ContaDetalhesDto {

	private final Long id;
	private final String nome;
	private final BigDecimal saldo;
	private final String descricao;
	private final String tipoContaNome;
	private final String usuarioNome;
	private final String tipoContaIcone;
	private final String tipoContaCor;
	private final boolean incluiSoma;
	private final boolean ativo;
	
	private Long tipoContaId;

	public ContaDetalhesDto(Conta conta) {
		id = conta.getId();
		nome = conta.getNome();
		descricao = conta.getDescricao();
		saldo = conta.getSaldo();
		ativo = conta.getAtivo();
		incluiSoma = conta.getIncluiSoma();
		tipoContaId = conta.getTipo().getId();
		tipoContaNome = conta.getTipo().getNome();
		tipoContaIcone = conta.getTipo().getIcone();
		tipoContaCor = conta.getTipo().getCor();
		usuarioNome = conta.getUsuario().getNome();
	}

}
