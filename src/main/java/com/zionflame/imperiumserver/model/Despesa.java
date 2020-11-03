package com.zionflame.imperiumserver.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import org.hibernate.annotations.Where;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Where(clause = "deletado != 1")
public class Despesa extends Transacao implements Serializable {

	private static final long serialVersionUID = -5798144720709675560L;

	private boolean concluida;
	private boolean deletado;
	@ManyToOne
	private Conta conta;
	@OneToOne
	private Categoria categoria;

	public Despesa() {
	}

	public Despesa(BigDecimal valor, String descricao, LocalDate data, LocalTime hora, boolean concluida) {
		super(valor, descricao, data, hora);
		this.concluida = concluida;
	}

}
