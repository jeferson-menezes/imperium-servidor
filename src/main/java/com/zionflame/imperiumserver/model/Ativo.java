package com.zionflame.imperiumserver.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
public class Ativo {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String nome;
	private String codigo;
	
	@OneToOne
	private TipoAtivo tipoAtivo;
	
	@OneToOne
	private Setor setor;
	
	@OneToOne
	private Imagem imagem;

	public Ativo() {

	}

	public Ativo(String nome, String codigo, Setor setor, TipoAtivo tipo) {
		super();
		this.nome = nome;
		this.codigo = codigo;
		this.setor = setor;
		this.tipoAtivo = tipo;
	}

}
