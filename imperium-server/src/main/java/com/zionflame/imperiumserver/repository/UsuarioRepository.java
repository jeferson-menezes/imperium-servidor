package com.zionflame.imperiumserver.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.zionflame.imperiumserver.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

	Usuario findByEmail(String email);

}
