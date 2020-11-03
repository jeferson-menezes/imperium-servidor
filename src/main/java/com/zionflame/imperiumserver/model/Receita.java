package com.zionflame.imperiumserver.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Receita extends Transacao implements Serializable {

	private static final long serialVersionUID = 81845211939237479L;


	private boolean concluida;
	private boolean deletado;
	@ManyToOne
	private Conta conta;
	@OneToOne
	private Categoria categoria;

	public Receita() {
	}

	public Receita(BigDecimal valor, String descricao, LocalDate data, LocalTime hora, boolean concluida) {
		super(valor, descricao, data, hora);
		this.concluida = concluida;
	}
}
