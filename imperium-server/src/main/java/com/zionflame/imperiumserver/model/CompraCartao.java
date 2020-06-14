package com.zionflame.imperiumserver.model;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class CompraCartao extends Transacao implements Serializable {

	private static final long serialVersionUID = 2404334881083915307L;
	
	private int parcelas;
	private BigDecimal valorParcela;
	@ManyToOne
	private Cartao cartao;
	@OneToOne
	private Categoria categoria;
	
	public CompraCartao() {
	}
}
