package com.zionflame.imperiumserver.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.zionflame.imperiumserver.model.Transferencia;
import com.zionflame.imperiumserver.model.Usuario;

public interface TransferenciaRepository extends JpaRepository<Transferencia, Long> {

	Page<Transferencia> findByContaOrigemUsuario(Usuario usuario, Pageable pageable);
}
