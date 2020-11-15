package com.zionflame.imperiumserver.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.zionflame.imperiumserver.model.Historia;
import com.zionflame.imperiumserver.model.enums.Natureza;

public interface HistoriaRepository extends JpaRepository<Historia, Long>{


	Page<Historia> findByUsuarioId(Long usuarioId, Pageable pageable);

	Optional<Historia> findByNaturezaAndTransacaoIdAndUsuarioId(Natureza natureza, Long transacaoId, Long id);

}
