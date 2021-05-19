package com.zionflame.imperiumserver.controller.form;

import java.math.BigDecimal;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import com.zionflame.imperiumserver.model.Bandeira;
import com.zionflame.imperiumserver.model.Cartao;
import com.zionflame.imperiumserver.model.Usuario;
import com.zionflame.imperiumserver.repository.BandeiraRepository;
import com.zionflame.imperiumserver.repository.UsuarioRepository;

import lombok.Setter;

@Setter
public class CartaoForm {

	private String nome;
	private BigDecimal limite;
	private Long bandeiraId;
	private int diaFechamento;
	private int diaVencimento;
	private Long usuarioId;

	public Cartao converter(BandeiraRepository bandeiraRepository, UsuarioRepository usuarioRepository) {

		Optional<Bandeira> bandeiraOptional = bandeiraRepository.findById(bandeiraId);

		if (!bandeiraOptional.isPresent()) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Bandeira inválida");
		}

		Optional<Usuario> usuarioOptional = usuarioRepository.findById(usuarioId);

		if (!usuarioOptional.isPresent()) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Usuário inválido");
		}

		return new Cartao(nome, limite, bandeiraOptional.get(), diaFechamento, diaVencimento, usuarioOptional.get());
	}

}
