package com.zionflame.imperiumserver.controller.dto;

import java.util.List;

import com.zionflame.imperiumserver.model.Perfil;
import com.zionflame.imperiumserver.model.Usuario;

import lombok.Getter;

@Getter
public class UsuarioDto {

	private Long id;
	private String nome;
	private String email;
	private List<Perfil> perfis;

	public UsuarioDto(Usuario usuario) {
		id = usuario.getId();
		nome = usuario.getNome();
		email = usuario.getEmail();
		perfis = usuario.getPerfis();
	}

}
