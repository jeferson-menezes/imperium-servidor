package com.zionflame.imperiumserver.controller;

import java.util.Optional;

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
import com.zionflame.imperiumserver.controller.form.TokenForm;
import com.zionflame.imperiumserver.model.Usuario;
import com.zionflame.imperiumserver.repository.UsuarioRepository;

@RestController
@RequestMapping("/auth")
public class AuthController {

	@Autowired
	private AuthenticationManager authManager;

	@Autowired
	private TokenService tokenService;

	@Autowired
	private UsuarioRepository usuarioRepository;

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

	@PostMapping("/valida")
	public ResponseEntity<?> valida(@RequestBody TokenForm form) {
		if (!tokenService.isTokenValido(form.getToken())) {
			return ResponseEntity.notFound().build();
		}

		Long usuarioId = tokenService.getIdUsuario(form.getToken());

		Optional<Usuario> optional = usuarioRepository.findById(usuarioId);

		if (!optional.isPresent()) {
			return ResponseEntity.notFound().build();
		}

		return ResponseEntity.ok(new LogadoDto(form.getToken(), optional.get()));
	}
}
