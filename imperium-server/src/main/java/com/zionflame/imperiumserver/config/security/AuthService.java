package com.zionflame.imperiumserver.config.security;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.zionflame.imperiumserver.model.Usuario;
import com.zionflame.imperiumserver.repository.UsuarioRepository;

@Service
public class AuthService implements UserDetailsService {

	@Autowired
	private UsuarioRepository repository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<Usuario> optional = repository.findByEmail(username);

		if (!optional.isPresent()) {
			throw new UsernameNotFoundException("Dados inválidos!");
		}

		return optional.get();
	}

}
