package com.zionflame.imperiumserver.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.zionflame.imperiumserver.helper.DateHelper;
import com.zionflame.imperiumserver.model.enums.StatusFatura;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
public class Fatura implements Serializable {

	private static final long serialVersionUID = -7177795657817231172L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private LocalDate abertura;
	private LocalDate fechamento;
	private LocalDate vencimento;
	private int ano;
	private int mes;
	@Enumerated(EnumType.STRING)
	private StatusFatura status;
	@ManyToOne
	private Cartao cartao;
	@OneToMany(mappedBy = "fatura")
	private List<ItemFatura> itens;

	public Fatura() {

	}

	public Fatura(LocalDate abertura, LocalDate fechamento, LocalDate vencimento, int ano, int mes, StatusFatura status,
			Cartao cartao) {
		this.abertura = abertura;
		this.fechamento = fechamento;
		this.vencimento = vencimento;
		this.ano = ano;
		this.mes = mes;
		this.status = status;
		this.cartao = cartao;
	}

	public String getMesExtenso() {
		return DateHelper.getMesExtenso(mes);
	}

}
