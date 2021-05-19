package com.zionflame.imperiumserver.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.zionflame.imperiumserver.model.enums.Natureza;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Historia {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private Long transacaoId;
	@ManyToOne
	private Usuario usuario;

	@Enumerated(EnumType.STRING)
	private Natureza natureza;

	private LocalDate data;
	private LocalTime hora;

	private String descricao;
	private BigDecimal valor;
	@ManyToOne
	private Conta conta;
	

	public Historia() {

	}

	public Historia(Transacao transacao, Natureza natureza, Usuario usuario, Conta conta) {
		this.data = transacao.getData();
		this.hora = transacao.getHora();
		this.transacaoId = transacao.getId();
		this.descricao = transacao.getDescricao();
		this.valor = transacao.getValor();
		this.usuario = usuario;
		this.natureza = natureza;
		this.conta = conta;
	}
}
