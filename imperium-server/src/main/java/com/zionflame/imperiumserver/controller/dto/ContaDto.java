package com.zionflame.imperiumserver.controller.dto;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import com.zionflame.imperiumserver.model.Conta;

import lombok.Getter;

@Getter
public class ContaDto {

	private Long id;
	private String nome;
	private BigDecimal saldo;
	private String descricao;
	private String tipoContaNome;
	private String UsuarioNome;

	public ContaDto(Conta conta) {
		id = conta.getId();
		nome = conta.getNome();
		descricao = conta.getDescricao();
		saldo = conta.getSaldo();
		tipoContaNome = conta.getTipo().getNome();
		UsuarioNome = conta.getUsuario().getNome();
	}

	public static List<ContaDto> converter(List<Conta> contas) {
		return contas.stream().map(ContaDto::new).collect(Collectors.toList());
	}

}
