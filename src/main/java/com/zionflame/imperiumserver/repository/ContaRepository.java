package com.zionflame.imperiumserver.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.zionflame.imperiumserver.model.Conta;
import com.zionflame.imperiumserver.model.Usuario;

public interface ContaRepository extends JpaRepository<Conta, Long>{

	List<Conta> findByUsuarioId(Long usuarioId);

	Optional<Conta> findByIdAndUsuario(Long id, Usuario usuario);

}
