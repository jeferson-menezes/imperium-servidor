package com.zionflame.imperiumserver.controller.form;

import java.math.BigDecimal;

import com.zionflame.imperiumserver.config.exeption.BadRequestException;
import com.zionflame.imperiumserver.model.Conta;
import com.zionflame.imperiumserver.model.TipoConta;
import com.zionflame.imperiumserver.model.Usuario;
import com.zionflame.imperiumserver.repository.TipoContaRepository;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ContaForm {

	private String nome;
	private String descricao;
	private BigDecimal saldo;
	private boolean incluiSoma;
	private Long tipoContaId;
	private boolean ativo;

	public Conta converter(TipoContaRepository tipoContaRepository, Usuario usuario) {
		TipoConta tipoConta = tipoContaRepository.findById(tipoContaId)
				.orElseThrow(() -> new BadRequestException("Usuário inválido"));
		return new Conta(nome, saldo, descricao, incluiSoma, ativo, usuario, tipoConta);
	}
}
