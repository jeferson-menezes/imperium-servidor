package com.zionflame.imperiumserver.controller.form;

import com.zionflame.imperiumserver.config.exeption.BadRequestException;
import com.zionflame.imperiumserver.model.Ativo;
import com.zionflame.imperiumserver.model.Negociacao;
import com.zionflame.imperiumserver.model.Usuario;
import com.zionflame.imperiumserver.repository.AtivoRepository;

public class NegociacaoForm {

	private Long ativoId;

	public Negociacao converter(AtivoRepository ativoRepository, Usuario usuario) {

		Ativo ativo = ativoRepository.findById(ativoId).orElseThrow(() -> new BadRequestException("Ativo inválido"));
		return null;
	}

}
