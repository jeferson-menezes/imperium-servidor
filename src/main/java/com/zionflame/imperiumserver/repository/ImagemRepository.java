package com.zionflame.imperiumserver.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.zionflame.imperiumserver.model.Imagem;
import com.zionflame.imperiumserver.model.Usuario;

public interface ImagemRepository extends JpaRepository<Imagem, Long> {

	Optional<Imagem> findByIdAndUsuario(Long id, Usuario usuario);

}
