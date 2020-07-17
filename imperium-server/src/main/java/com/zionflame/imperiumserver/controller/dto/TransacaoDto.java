package com.zionflame.imperiumserver.controller.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

import com.zionflame.imperiumserver.model.Transacao;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class TransacaoDto implements Serializable {

	private static final long serialVersionUID = 7268111314451486526L;

	protected Long id;
	protected BigDecimal valor;
	protected String descricao;
	protected LocalDate data;
	protected LocalTime	 hora;

	public TransacaoDto(Transacao transacao) {

		this.id = transacao.getId();
		this.valor = transacao.getValor();
		this.descricao = transacao.getDescricao();
		this.data = transacao.getData();
		this.hora = transacao.getHora();
	}

}
