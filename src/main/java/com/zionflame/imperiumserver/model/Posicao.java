package com.zionflame.imperiumserver.model;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Posicao {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	protected Long id;

	@OneToOne
	private Ativo ativo;

	@ManyToOne
	private Usuario usuario;

	private Integer quantidade;

	private BigDecimal precoCotacao;

	private BigDecimal valorTotal;

	public Posicao() {

	}
}
