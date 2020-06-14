package com.zionflame.imperiumserver.controller.form;

import com.zionflame.imperiumserver.model.Usuario;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsuarioForm {

	private String email;
	private String senha;
	private String nome;

	public Usuario converter() {
		return new Usuario(nome, email, senha);
	}
}
