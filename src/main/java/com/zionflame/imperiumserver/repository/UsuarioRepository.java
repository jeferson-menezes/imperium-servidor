package com.zionflame.imperiumserver.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.zionflame.imperiumserver.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

	Optional<Usuario> findByEmail(String email);

}
