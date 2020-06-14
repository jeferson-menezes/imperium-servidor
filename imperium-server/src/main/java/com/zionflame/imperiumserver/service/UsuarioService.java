package com.zionflame.imperiumserver.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zionflame.imperiumserver.controller.form.UsuarioForm;
import com.zionflame.imperiumserver.model.Usuario;
import com.zionflame.imperiumserver.repository.UsuarioRepository;

@Service
public class UsuarioService {

	@Autowired
	private UsuarioRepository usuarioRepository;

	public Usuario buscarPorEmail(String email) {
		return usuarioRepository.findByEmail(email);
	}

	public Usuario adicionaUsuario(UsuarioForm form) {
		Usuario usuario = form.converter();
//		usuario.setSenha(new BCryptPasswordEncoder().encode(form.getSenha()));
		return usuarioRepository.save(usuario);
	}

}
