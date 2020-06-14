package com.zionflame.imperiumserver.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zionflame.imperiumserver.model.Despesa;
import com.zionflame.imperiumserver.repository.DespesaRepository;

@Service
public class DespesaService {

	@Autowired
	private DespesaRepository despesaRepository;

	public Despesa adicionaDespesa(Despesa despesa) {
		return despesaRepository.save(despesa);
	}

	public Despesa buscarPorId(Long id) {
		Optional<Despesa> optional = despesaRepository.findById(id);
		if (!optional.isPresent())
			return null;
		return optional.get();
	}
}
