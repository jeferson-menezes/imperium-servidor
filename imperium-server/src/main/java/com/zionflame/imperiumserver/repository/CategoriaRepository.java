package com.zionflame.imperiumserver.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.zionflame.imperiumserver.model.Categoria;
import com.zionflame.imperiumserver.model.enums.NaturezaCategoria;

public interface CategoriaRepository extends JpaRepository<Categoria, Long>{

	List<Categoria> findByNatureza(NaturezaCategoria natureza);

}
