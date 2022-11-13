package com.zionflame.imperiumserver.controller.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.springframework.data.domain.Page;

import com.zionflame.imperiumserver.model.Provento;
import com.zionflame.imperiumserver.model.enums.Evento;

import lombok.Getter;

@Getter
public class ProventoDto {

	private final Long id;
	private final Evento evento;
	private final LocalDate pagoEm;
	private final Integer quantidade;
	private final LocalDate aprovadoEm;
	private final BigDecimal valorLiquido;
	private final BigDecimal precoUnitario;
	private final Long ativoId;
	private final String ativoNome;
	private final String ativoCodigo;
	private final Long ativoImagemId;

	public ProventoDto(Provento provento) {
		id = provento.getId();
		evento = provento.getEvento();
		pagoEm = provento.getPagoEm();
		quantidade = provento.getQuantidade();
		aprovadoEm = provento.getAprovadoEm();
		valorLiquido = provento.getValorLiquido();
		precoUnitario = provento.getPrecoUnitario();
		ativoId = provento.getAtivo().getId();
		ativoNome = provento.getAtivo().getNome();
		ativoCodigo = provento.getAtivo().getCodigo();
		ativoImagemId = provento.getAtivo().getImagem().getId();
	}

	public static Page<ProventoDto> converter(Page<Provento> proventos) {
		return proventos.map(ProventoDto::new);
	}

}
