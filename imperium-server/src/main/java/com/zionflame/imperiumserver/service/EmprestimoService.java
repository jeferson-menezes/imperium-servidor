package com.zionflame.imperiumserver.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zionflame.imperiumserver.model.Emprestimo;
import com.zionflame.imperiumserver.repository.EmprestimoRepository;

@Service
public class EmprestimoService {

	@Autowired
	private EmprestimoRepository emprestimoRepository;

	public Emprestimo adicionaEmprestimo(Emprestimo emprestimo) {
		return emprestimoRepository.save(emprestimo);
	}

	public Emprestimo buscarPorId(Long id) {
		Optional<Emprestimo> optional = emprestimoRepository.findById(id);
		if (!optional.isPresent())
			return null;
		return optional.get();
	}


}
