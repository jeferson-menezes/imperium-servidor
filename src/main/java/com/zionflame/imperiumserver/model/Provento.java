package com.zionflame.imperiumserver.model;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.zionflame.imperiumserver.model.enums.Evento;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
public class Provento {
	

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Enumerated(EnumType.STRING)
	private Evento evento;
	
	private LocalDate pagoEm;
	private Integer quantidade;
	private LocalDate aprovadoEm;
	private BigDecimal valorLiquido;
	private BigDecimal precoUnitario;
	
	@ManyToOne
	private Ativo ativo;
	
	@ManyToOne
	private Usuario usuario;
	
	public Provento() {
	
	}

	public Provento(Evento evento, LocalDate pagoEm, Integer quantidade, LocalDate aprovadoEm, BigDecimal valorLiquido,
			BigDecimal precoUnitario, Ativo ativo, Usuario usuario) {
		super();
		this.evento = evento;
		this.pagoEm = pagoEm;
		this.quantidade = quantidade;
		this.aprovadoEm = aprovadoEm;
		this.valorLiquido = valorLiquido;
		this.precoUnitario = precoUnitario;
		this.ativo = ativo;
		this.usuario = usuario;
	}



	
	

}
