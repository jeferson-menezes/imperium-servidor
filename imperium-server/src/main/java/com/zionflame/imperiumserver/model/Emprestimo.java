package com.zionflame.imperiumserver.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.zionflame.imperiumserver.model.enums.NaturezaEmprestimo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Emprestimo extends Transacao implements Serializable {

	private static final long serialVersionUID = -7079240843328282478L;
	
	private String pessoa;
	@Enumerated(EnumType.STRING)
	private NaturezaEmprestimo natureza;
	@ManyToOne
	private Conta conta;

	@ManyToOne
	private Usuario usuario;
	
	@OneToMany(mappedBy = "emprestimo")
	private List<AcaoEmprestimo> acoes;

	public Emprestimo() {

	}
}
