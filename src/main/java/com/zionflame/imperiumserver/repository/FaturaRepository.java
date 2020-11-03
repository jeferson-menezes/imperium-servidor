package com.zionflame.imperiumserver.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.zionflame.imperiumserver.model.Fatura;

public interface FaturaRepository extends JpaRepository<Fatura, Long>{

	Fatura findByAnoAndMesAndCartaoId(int year, int monthValue, Long id);

}
