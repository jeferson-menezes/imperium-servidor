package com.zionflame.imperiumserver.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.zionflame.imperiumserver.model.Transferencia;

public interface TransferenciaRepository extends JpaRepository<Transferencia, Long> {

	Page<Transferencia> findByContaOrigemUsuarioId(Long usuarioId, Pageable pageable);

}
