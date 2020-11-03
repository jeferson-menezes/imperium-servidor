package com.zionflame.imperiumserver.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
public class Transferencia extends Transacao implements Serializable {

	private static final long serialVersionUID = 1197546089853376060L;
	@ManyToOne
	private Conta contaOrigem;
	@ManyToOne
	private Conta contaDestino;

	public Transferencia() {

	}

	public Transferencia(BigDecimal valor, String descricao, LocalDate data, LocalTime hora) {
		super(valor, descricao, data, hora);
	}
}
