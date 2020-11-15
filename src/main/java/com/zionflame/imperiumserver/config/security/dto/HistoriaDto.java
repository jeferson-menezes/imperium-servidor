package com.zionflame.imperiumserver.config.security.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

import org.springframework.data.domain.Page;

import com.zionflame.imperiumserver.model.Historia;
import com.zionflame.imperiumserver.model.enums.Natureza;

import lombok.Getter;

@Getter
public class HistoriaDto {

	private Long id;
	private Long transacaoId;
	private Natureza natureza;
	private LocalDate data;
	private LocalTime hora;
	private String descricao;
	private BigDecimal valor;

	public HistoriaDto(Historia historia) {
		id = historia.getId();
		transacaoId = historia.getTransacaoId();
		natureza = historia.getNatureza();
		data = historia.getData();
		hora = historia.getHora();
		descricao = historia.getDescricao();
		valor = historia.getValor();
	}

	public static Page<HistoriaDto> converter(Page<Historia> historias) {
		return historias.map(HistoriaDto::new);
	}

}
