package com.zionflame.imperiumserver.config.security.dto;

import com.zionflame.imperiumserver.controller.dto.UsuarioDto;
import com.zionflame.imperiumserver.model.Usuario;

import lombok.Getter;

@Getter
public class LogadoDto {

	private String token;
	private UsuarioDto usuario;

	public LogadoDto(String token, Usuario usuario) {
		this.token = token;
		this.usuario = new UsuarioDto(usuario);
	}

}
