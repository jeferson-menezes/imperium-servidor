package com.zionflame.imperiumserver.model;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
public class Conta implements Serializable {

	private static final long serialVersionUID = -4269420377927345556L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String nome;
	private BigDecimal saldo;
	private String descricao;
	private Boolean incluiSoma;
	private Boolean ativo;
	@ManyToOne
	private Usuario usuario;
	@OneToOne
	private TipoConta tipo;

	public Conta() {

	}

	public Conta(String nome, BigDecimal saldo, String descricao, boolean incluiSoma, boolean ativo, Usuario usuario,
			TipoConta tipo) {
		this.nome = nome;
		this.saldo = saldo;
		this.descricao = descricao;
		this.incluiSoma = incluiSoma;
		this.ativo = ativo;
		this.usuario = usuario;
		this.tipo = tipo;
	}

	public boolean subtrai(BigDecimal valor) {
		if (saldo.compareTo(valor) == -1)
			return false;
		saldo = saldo.subtract(valor);
		return true;
	}

	public void soma(BigDecimal valor) {
		saldo = saldo.add(valor);
	}

}
