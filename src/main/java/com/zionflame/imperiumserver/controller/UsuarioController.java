package com.zionflame.imperiumserver.controller;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.zionflame.imperiumserver.controller.dto.MensagemDto;
import com.zionflame.imperiumserver.controller.dto.UsuarioDto;
import com.zionflame.imperiumserver.controller.form.UsuarioForm;
import com.zionflame.imperiumserver.model.Usuario;
import com.zionflame.imperiumserver.service.UsuarioService;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

	@Autowired
	public UsuarioService usuarioService;

	@PostMapping
	public ResponseEntity<?> adicionar(@RequestBody UsuarioForm form, UriComponentsBuilder uriBuilder){

		if (usuarioService.buscarPorEmail(form.getEmail()) != null) {
			return ResponseEntity.badRequest().body(new MensagemDto("Email já cadastrado!"));
		}

		Usuario usuario = usuarioService.adicionaUsuario(form);
		
		URI uri = uriBuilder.path("/usuarios/{id}").buildAndExpand(usuario.getId()).toUri();
		return ResponseEntity.created(uri).body(new UsuarioDto(usuario));
	}
}
