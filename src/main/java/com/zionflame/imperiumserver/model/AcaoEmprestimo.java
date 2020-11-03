package com.zionflame.imperiumserver.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
public class AcaoEmprestimo extends Transacao implements Serializable {

	private static final long serialVersionUID = 5093075232777023288L;

	@ManyToOne
	private Conta conta;
	@ManyToOne
	private Emprestimo emprestimo;

	public AcaoEmprestimo() {

	}

	public AcaoEmprestimo(BigDecimal valor, String descricao, LocalDate data, LocalTime hora){
		super(valor, descricao, data, hora);
	}
}
