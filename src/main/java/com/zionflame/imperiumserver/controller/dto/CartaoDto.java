package com.zionflame.imperiumserver.controller.dto;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import com.zionflame.imperiumserver.model.Cartao;

import lombok.Getter;

@Getter
public class CartaoDto {

	private final Long id;
	private String nome;
	private String bandeiraNome;
	private int diaFechamento;
	private int diaVencimento;
	private BigDecimal limite;
	private String bandeiraCor;
	private String bandeiraIcone;

	public CartaoDto(Cartao cartao) {
		id = cartao.getId();
		nome = cartao.getNome();
		bandeiraNome = cartao.getBandeira().getNome();
		bandeiraIcone = cartao.getBandeira().getIcone();
		bandeiraCor = cartao.getBandeira().getCor();
		diaFechamento = cartao.getDiaFechamento();
		diaVencimento = cartao.getDiaVencimento();
		limite = cartao.getLimite();
	}

	public static List<CartaoDto> converter(List<Cartao> cartoes) {
		return cartoes.stream().map(CartaoDto::new).collect(Collectors.toList());
	}

}
