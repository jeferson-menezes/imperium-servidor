package com.zionflame.imperiumserver.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.zionflame.imperiumserver.model.Conta;

public interface ContaRepository extends JpaRepository<Conta, Long>{

	List<Conta> findByUsuarioId(Long usuarioId);

}
