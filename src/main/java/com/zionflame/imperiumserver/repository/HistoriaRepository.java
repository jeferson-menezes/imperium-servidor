package com.zionflame.imperiumserver.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.zionflame.imperiumserver.model.Historia;
import com.zionflame.imperiumserver.model.Usuario;
import com.zionflame.imperiumserver.model.enums.Natureza;

public interface HistoriaRepository extends JpaRepository<Historia, Long>, JpaSpecificationExecutor<Historia>{


	Page<Historia> findByUsuarioId(Long usuarioId, Pageable pageable);

	Optional<Historia> findByNaturezaAndTransacaoIdAndUsuarioId(Natureza natureza, Long transacaoId, Long id);

	Page<Historia> findByContaId(Long contaId, Pageable pageable);

	Optional<Historia> findByNaturezaAndTransacaoIdAndUsuario(Natureza despesa, Long despesaId, Usuario usuario);

	Page<Historia> findByUsuario(Usuario usuario, Pageable pageable);

}
