package com.zionflame.imperiumserver.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class ItemFatura extends Transacao implements Serializable {

	private static final long serialVersionUID = -5049642652390548485L;

	private String parcela;
	@ManyToOne
	private Fatura fatura;
	@ManyToOne
	private CompraCartao compra;

	public ItemFatura() {
	}

	public ItemFatura(BigDecimal valor, String descricao, LocalDate data, LocalTime hora, String parcela,
			Fatura fatura, CompraCartao compra) {
		super(valor, descricao, data, hora);
		this.parcela = parcela;
		this.fatura = fatura;
		this.compra = compra;
	}
}
