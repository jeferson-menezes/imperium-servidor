package com.zionflame.imperiumserver.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.zionflame.imperiumserver.model.enums.NaturezaCategoria;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Categoria extends Visual implements Serializable {

	private static final long serialVersionUID = 4277227197737258219L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String nome;
	private String descricao;
	@Enumerated(EnumType.STRING)
	private NaturezaCategoria natureza;
	private boolean ativo;

	public Categoria() {

	}

	public Categoria(String nome, String descricao, NaturezaCategoria natureza, boolean ativo, String cor,
			String icone) {
		super(cor, icone);
		this.nome = nome;
		this.descricao = descricao;
		this.natureza = natureza;
		this.ativo = ativo;
	}

}
