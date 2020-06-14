package com.zionflame.imperiumserver.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Despesa extends Transacao implements Serializable {

	private static final long serialVersionUID = -5798144720709675560L;

	private boolean excluido = false;
	private boolean concluida;
	@ManyToOne
	private Conta conta;
	@OneToOne
	private Categoria categoria;

	public Despesa() {
	}

	public Despesa(BigDecimal valor, String descricao, LocalDateTime data, LocalDateTime hora, boolean concluida) {
		super(valor, descricao, data, hora);
		this.concluida = concluida;
	}

}
