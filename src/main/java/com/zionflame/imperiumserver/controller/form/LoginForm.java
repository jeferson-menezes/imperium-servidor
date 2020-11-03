package com.zionflame.imperiumserver.controller.form;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import lombok.Setter;

@Setter
public class LoginForm {

	private String email;
	private String senha;
	public UsernamePasswordAuthenticationToken converter() {
		return new UsernamePasswordAuthenticationToken(email, senha);
	}
}
