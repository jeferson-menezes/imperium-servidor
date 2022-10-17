package com.zionflame.imperiumserver.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.zionflame.imperiumserver.model.Despesa;

public interface DespesaRepository extends JpaRepository<Despesa, Long> , JpaSpecificationExecutor<Despesa>{

	Page<Despesa> findByContaUsuarioId(Long usuarioId, Pageable pageable);

	Page<Despesa> findByDataAndContaUsuarioId(LocalDate data, Long id, Pageable pageable);

	Page<Despesa> findByData(LocalDate data, Pageable pageable);

	Page<Despesa> findByContaUsuarioIdAndDescricaoContaining(Long id, String descricao, Pageable pageable);

	Page<Despesa> findByDataBetweenAndContaUsuarioId(LocalDate inicio, LocalDate fim, Long id, Pageable pageable);

	List<Despesa> findByDataBetweenAndContaUsuarioId(LocalDate inicio, LocalDate fim, Long id);

}
