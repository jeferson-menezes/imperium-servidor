package com.zionflame.imperiumserver.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zionflame.imperiumserver.model.Historia;
import com.zionflame.imperiumserver.repository.HistoriaRepository;

@Service
public class HistoriaService {

	@Autowired
	private HistoriaRepository historiaRepository;

	public void adiciona(Historia historia) {
		historiaRepository.save(historia);
	}

	public void exclui(Historia historia) {
		Optional<Historia> optional = historiaRepository.findByNaturezaAndTransacaoIdAndUsuarioId(historia.getNatureza(),
				historia.getTransacaoId(), historia.getUsuario().getId());

		if (!optional.isPresent())
			return;
		
		Historia h = optional.get();
		historiaRepository.delete(h);

	}

	public void atualiza(Historia historia) {

		Optional<Historia> optional = historiaRepository.findByNaturezaAndTransacaoIdAndUsuarioId(historia.getNatureza(),
				historia.getTransacaoId(), historia.getUsuario().getId());

		if (!optional.isPresent()) {
			this.adiciona(historia);
			return;
		}

		Historia h = optional.get();
		h.setData(historia.getData());
		h.setHora(historia.getHora());
		h.setDescricao(historia.getDescricao());
		h.setValor(historia.getValor());
		h.setUsuario(historia.getUsuario());

		historiaRepository.save(h);
	}
}
