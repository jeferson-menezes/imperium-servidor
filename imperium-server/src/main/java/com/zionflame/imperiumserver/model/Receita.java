package com.zionflame.imperiumserver.model;

import java.io.Serializable;

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
	@ManyToOne
	private Conta conta;
	@OneToOne
	private Categoria categoria;

	public Receita() {
	}

}
