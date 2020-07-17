package com.zionflame.imperiumserver.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.zionflame.imperiumserver.model.Receita;

public interface ReceitaRepository extends JpaRepository<Receita, Long> {

	Page<Receita> findByContaUsuarioId(Long usuarioId, Pageable pageable);

	Page<Receita> findByDataAndContaUsuarioId(LocalDate data, Long id, Pageable pageable);

	Page<Receita> findByContaUsuarioIdAndDescricaoContaining(Long id, String descricao, Pageable pageable);

	Page<Receita> findByDataBetweenAndContaUsuarioId(LocalDate inicio, LocalDate fim, Long id, Pageable pageable);

	List<Receita> findByDataBetweenAndContaUsuarioId(LocalDate inicio, LocalDate fim, Long id);

}
