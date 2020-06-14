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
	@Enumerated(EnumType.STRING)
	private StatusFatura status;
	@ManyToOne
	private Cartao cartao;
	@OneToMany(mappedBy = "fatura")
	private List<ItemFatura> itens;
	
	public Fatura() {
	
	}
}
