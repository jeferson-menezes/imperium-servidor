package com.zionflame.imperiumserver.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.zionflame.imperiumserver.model.Cartao;
import com.zionflame.imperiumserver.model.Conta;

public interface CartaoRepository extends JpaRepository<Cartao, Long>{

	List<Cartao> findByUsuarioId(Long id);

}
