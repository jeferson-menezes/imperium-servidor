package com.zionflame.imperiumserver.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.zionflame.imperiumserver.model.Provento;
import com.zionflame.imperiumserver.model.Usuario;

public interface ProventoRepository extends JpaRepository<Provento, Long>, JpaSpecificationExecutor<Provento> {

	Optional<Provento> findByIdAndUsuario(Long id, Usuario usuario);

}
