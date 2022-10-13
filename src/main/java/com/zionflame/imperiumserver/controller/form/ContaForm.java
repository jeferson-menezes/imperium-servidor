package com.zionflame.imperiumserver.controller.form;

import java.math.BigDecimal;

import javax.validation.constraints.NotNull;

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

	@NotNull
	private String nome;
	@NotNull
	private String descricao;
	@NotNull
	private BigDecimal saldo;
	@NotNull
	private Boolean incluiSoma;
	@NotNull
	private Long tipoContaId;
	@NotNull
	private Boolean ativo;

	public Conta converter(TipoContaRepository tipoContaRepository, Usuario usuario) {
		TipoConta tipoConta = tipoContaRepository.findById(tipoContaId)
				.orElseThrow(() -> new BadRequestException("Usuário inválido"));
		return new Conta(nome, saldo, descricao, incluiSoma, ativo, usuario, tipoConta);
	}
}
