package com.zionflame.imperiumserver.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zionflame.imperiumserver.config.security.TokenService;
import com.zionflame.imperiumserver.config.security.dto.LogadoDto;
import com.zionflame.imperiumserver.controller.form.LoginForm;

@RestController
@RequestMapping("/auth")
public class AuthController {

	@Autowired
	private AuthenticationManager authManager;

	@Autowired
	private TokenService tokenService;

	@PostMapping
	public ResponseEntity<?> autenticar(@RequestBody LoginForm form) {

		UsernamePasswordAuthenticationToken dadosLogin = form.converter();

		try {
			Authentication authenticate = authManager.authenticate(dadosLogin);
			LogadoDto logado = tokenService.gerarToken(authenticate);
			return ResponseEntity.ok(logado);
		} catch (AuthenticationException e) {
			return ResponseEntity.badRequest().build();
		}
	}
}
