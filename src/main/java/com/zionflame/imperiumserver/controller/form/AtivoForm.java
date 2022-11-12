package com.zionflame.imperiumserver.controller.form;

import javax.validation.constraints.NotNull;

import com.zionflame.imperiumserver.config.exeption.BadRequestException;
import com.zionflame.imperiumserver.model.Ativo;
import com.zionflame.imperiumserver.model.Setor;
import com.zionflame.imperiumserver.model.TipoAtivo;
import com.zionflame.imperiumserver.repository.SetorRepository;
import com.zionflame.imperiumserver.repository.TipoAtivoRepository;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AtivoForm {

	@NotNull
	private String nome;

	@NotNull
	private String codigo;

	@NotNull
	private Long setorId;

	@NotNull
	private Long tipoAtivoId;

	public Ativo converter(SetorRepository setorRepository, TipoAtivoRepository tipoAtivoRepository) {

		Setor setor = setorRepository.findById(setorId)
				.orElseThrow(() -> new BadRequestException("Setor inválido!"));

		TipoAtivo tipoAtivo = tipoAtivoRepository.findById(tipoAtivoId)
				.orElseThrow(() -> new BadRequestException("Tipo ativo inválido!"));

		return new Ativo(nome, codigo, setor, tipoAtivo);
	}

}
