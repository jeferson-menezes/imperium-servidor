package com.zionflame.imperiumserver.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zionflame.imperiumserver.model.AcaoEmprestimo;
import com.zionflame.imperiumserver.repository.AcaoEmprestimoRepository;

@Service
public class AcaoEmprestimoService {

	@Autowired
	private AcaoEmprestimoRepository acaoEmprestimoRepository;

	public AcaoEmprestimo adicionaAcao(AcaoEmprestimo acao) {
		return acaoEmprestimoRepository.save(acao);
	}

	public AcaoEmprestimo buscarPorId(Long id) {
		Optional<AcaoEmprestimo> optional = acaoEmprestimoRepository.findById(id);
		if (!optional.isPresent())
			return null;
		return optional.get();
	}

	public void excluir(AcaoEmprestimo acao) {
		acaoEmprestimoRepository.delete(acao);
	}

}
