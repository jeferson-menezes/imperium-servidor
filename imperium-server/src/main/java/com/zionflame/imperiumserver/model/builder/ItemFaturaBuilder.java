package com.zionflame.imperiumserver.model.builder;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import com.zionflame.imperiumserver.model.CompraCartao;
import com.zionflame.imperiumserver.model.Fatura;
import com.zionflame.imperiumserver.model.ItemFatura;

public class ItemFaturaBuilder {

	private BigDecimal valor;
	private String descricao;
	private LocalDate data;
	private LocalTime hora;
	private String parcela;
	private Fatura fatura;
	private CompraCartao compra;

	public ItemFaturaBuilder comValor(BigDecimal valor) {
		this.valor = valor;
		return this;
	}

	public ItemFaturaBuilder comDescricao(String descricao) {
		this.descricao = descricao;
		return this;
	}

	public ItemFaturaBuilder comData(LocalDate data) {
		this.data = data;
		return this;
	}

	public ItemFaturaBuilder comHora(LocalTime hora) {
		this.hora = hora;
		return this;
	}

	public ItemFaturaBuilder comParcela(String parcela) {
		this.parcela = parcela;
		return this;
	}

	public ItemFaturaBuilder comFatura(Fatura fatura) {
		this.fatura = fatura;
		return this;
	}

	public ItemFaturaBuilder comCompra(CompraCartao compra) {
		this.compra = compra;
		return this;
	}

	public ItemFatura build() {
		return new ItemFatura(valor, descricao, data, hora, parcela, fatura, compra);
	}
}
