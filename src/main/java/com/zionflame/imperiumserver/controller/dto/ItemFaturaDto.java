package com.zionflame.imperiumserver.controller.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

import com.zionflame.imperiumserver.model.ItemFatura;

import lombok.Getter;

@Getter
public class ItemFaturaDto {

	private Long id;
	private BigDecimal valor;
	private String parcela;
	private LocalTime hora;
	private LocalDate data;
	private String descricao;

	public ItemFaturaDto(ItemFatura item) {
		id = item.getId();
		valor = item.getValor();
		parcela = item.getParcela();
		hora = item.getHora();
		data = item.getData();
		descricao = item.getDescricao();

	}

	public static List<ItemFaturaDto> converter(List<ItemFatura> itens) {
		return itens.stream().map(ItemFaturaDto::new).collect(Collectors.toList());
	}

}
