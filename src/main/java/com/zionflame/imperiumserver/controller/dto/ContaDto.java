package com.zionflame.imperiumserver.controller.dto;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import com.zionflame.imperiumserver.model.Conta;

import lombok.Getter;

@Getter
public class ContaDto {

	private final Long id;
	private final String nome;
	private final BigDecimal saldo;
	private final String descricao;
	private final String tipoContaNome;
	private final String usuarioNome;
	private final String tipoContaIcone;
	private final String tipoContaCor;
	private final Boolean incluiSoma;
	private final Boolean ativo;

	public ContaDto(Conta conta) {
		id = conta.getId();
		nome = conta.getNome();
		descricao = conta.getDescricao();
		saldo = conta.getSaldo();
		ativo = conta.getAtivo();
		incluiSoma = conta.getIncluiSoma();
		tipoContaNome = conta.getTipo().getNome();
		tipoContaIcone = conta.getTipo().getIcone();
		tipoContaCor = conta.getTipo().getCor();
		usuarioNome = conta.getUsuario().getNome();
	}

	public static List<ContaDto> converter(List<Conta> contas) {
		return contas.stream().map(ContaDto::new).collect(Collectors.toList());
	}

}
