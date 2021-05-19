package com.zionflame.imperiumserver.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.zionflame.imperiumserver.model.ItemFatura;
import com.zionflame.imperiumserver.model.enums.StatusFatura;

public interface ItemFaturaRepository extends JpaRepository<ItemFatura, Long>{

	List<ItemFatura> findByFaturaStatusNotAndCompraCartaoId(StatusFatura paga, Long id);

	
}
