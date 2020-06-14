package com.zionflame.imperiumserver.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@MappedSuperclass
public class Transacao implements Serializable {

	private static final long serialVersionUID = 3588824725801378139L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	protected Long id;
	protected BigDecimal valor;
	protected String descricao;
	protected LocalDateTime data;
	protected LocalDateTime hora;

	public Transacao() {

	}

	public Transacao(BigDecimal valor, String descricao, LocalDateTime data, LocalDateTime hora) {
		this.valor = valor;
		this.descricao = descricao;
		this.data = data;
		this.hora = hora;
	}

}
