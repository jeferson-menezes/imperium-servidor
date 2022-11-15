package com.zionflame.imperiumserver.model;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import com.zionflame.imperiumserver.model.enums.Movimentacao;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
public class Negociacao {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private Integer quantidade;

	private BigDecimal preco;

	private BigDecimal valorTotal;

	private Movimentacao movimentacao;

	@OneToOne
	private Ativo ativo;

	@ManyToOne
	private Usuario usuario;

	public Negociacao() {
	}

	public Negociacao(Integer quantidade, BigDecimal preco, BigDecimal valorTotal, Movimentacao movimentacao,
			Ativo ativo, Usuario usuario) {
		this.quantidade = quantidade;
		this.preco = preco;
		this.valorTotal = valorTotal;
		this.movimentacao = movimentacao;
		this.ativo = ativo;
		this.usuario = usuario;
	}

}
