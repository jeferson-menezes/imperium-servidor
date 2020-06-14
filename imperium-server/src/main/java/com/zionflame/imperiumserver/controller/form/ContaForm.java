package com.zionflame.imperiumserver.controller.form;

import java.math.BigDecimal;
import java.util.Optional;

import com.zionflame.imperiumserver.model.Conta;
import com.zionflame.imperiumserver.model.TipoConta;
import com.zionflame.imperiumserver.model.Usuario;
import com.zionflame.imperiumserver.repository.TipoContaRepository;
import com.zionflame.imperiumserver.repository.UsuarioRepository;

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
	private Long usuarioId;
	private boolean ativo;
	
	public Conta converter(TipoContaRepository tipoContaRepository, UsuarioRepository usuarioRepository) {
		Optional<Usuario> usuario = usuarioRepository.findById(usuarioId);
		Optional<TipoConta> tipoConta = tipoContaRepository.findById(tipoContaId);
		if (!usuario.isPresent() || !tipoConta.isPresent())	return null;
		return new Conta(nome, saldo, descricao, incluiSoma, ativo, usuario.get(), tipoConta.get());
	}
}
