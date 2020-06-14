package com.zionflame.imperiumserver.model;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
public class Cartao implements Serializable {

	private static final long serialVersionUID = -3260177887670156312L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String nome;
	private BigDecimal limite;
	@OneToOne
	private Bandeira bandeira;
	private int diaFechamento;
	private int diaVencimento;
	
	@ManyToOne
	private Usuario usuario;
	
	public Cartao() {
	}
}
