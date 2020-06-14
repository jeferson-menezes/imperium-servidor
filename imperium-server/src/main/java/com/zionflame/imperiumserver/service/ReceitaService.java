package com.zionflame.imperiumserver.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zionflame.imperiumserver.model.Receita;
import com.zionflame.imperiumserver.repository.ReceitaRepository;

@Service
public class ReceitaService {

	@Autowired
	private ReceitaRepository receitaRepository;

	public Receita buscarPorId(Long id) {
		Optional<Receita> optional = receitaRepository.findById(id);
		if (!optional.isPresent())
			return null;
		return optional.get();
	}

	public Receita adicionaReceita(Receita receita) {
		return receitaRepository.save(receita);
	}
}
