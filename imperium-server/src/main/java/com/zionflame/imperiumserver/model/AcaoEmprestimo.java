package com.zionflame.imperiumserver.model;

import java.io.Serializable;

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

}
