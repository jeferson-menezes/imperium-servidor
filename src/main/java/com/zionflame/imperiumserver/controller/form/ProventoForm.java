package com.zionflame.imperiumserver.controller.form;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.zionflame.imperiumserver.config.exeption.BadRequestException;
import com.zionflame.imperiumserver.model.Ativo;
import com.zionflame.imperiumserver.model.Provento;
import com.zionflame.imperiumserver.model.Usuario;
import com.zionflame.imperiumserver.model.enums.Evento;
import com.zionflame.imperiumserver.repository.AtivoRepository;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProventoForm {

	private Evento evento;
	private LocalDate pagoEm;
	private Integer quantidade;
	private LocalDate aprovadoEm;
	private BigDecimal valorLiquido;
	private BigDecimal precoUnitario;

	private Long ativoId;

	public Provento converter(AtivoRepository ativoRepository, Usuario usuario) {

		Ativo ativo = ativoRepository.findById(ativoId).orElseThrow(() -> new BadRequestException("Ativo inválido!"));

		return new Provento(evento, pagoEm, quantidade, aprovadoEm, valorLiquido, precoUnitario, ativo, usuario);
	}

}
