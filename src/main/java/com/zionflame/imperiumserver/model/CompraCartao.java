package com.zionflame.imperiumserver.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class CompraCartao extends Transacao implements Serializable {

	private static final long serialVersionUID = 2404334881083915307L;

	private int parcelas;
	private BigDecimal valorParcela;
	@ManyToOne
	private Cartao cartao;
	@OneToOne
	private Categoria categoria;

	public CompraCartao() {
	}

	public CompraCartao(int parcelas, BigDecimal valorParcela, BigDecimal valor, String descricao, LocalDate data,
			LocalTime hora) {
		super(valor, descricao, data, hora);
		this.parcelas = parcelas;
		this.valorParcela = valorParcela;
	}

}
