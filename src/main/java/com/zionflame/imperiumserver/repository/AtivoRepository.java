package com.zionflame.imperiumserver.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.zionflame.imperiumserver.model.Ativo;

public interface AtivoRepository extends JpaRepository<Ativo, Long>, JpaSpecificationExecutor<Ativo>{

}
