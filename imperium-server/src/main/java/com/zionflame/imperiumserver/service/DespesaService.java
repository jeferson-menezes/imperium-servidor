package com.zionflame.imperiumserver.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

	public Page<Despesa> listarPorUsuario(Long usuarioId, Pageable pageable) {
		return despesaRepository.findByContaUsuarioId(usuarioId, pageable);
	}

	public Page<Despesa> listarPorUsuarioData(Long id, LocalDate data, Pageable pageable) {
		return despesaRepository.findByDataAndContaUsuarioId(data, id, pageable);
	}

	public Page<Despesa> listarPorUsuarioDescricao(Long id, String descricao, Pageable pageable) {
		return despesaRepository.findByContaUsuarioIdAndDescricaoContaining(id, descricao, pageable);
	}

	public Page<Despesa> filtrarPorUsuarioMes(Long id, LocalDate inicio, LocalDate fim, Pageable pageable) {
		return despesaRepository.findByDataBetweenAndContaUsuarioId(inicio, fim, id, pageable);
	}

	public List<Despesa> listarPorUsuarioMes(Long id, LocalDate inicio, LocalDate fim) {
		return despesaRepository.findByDataBetweenAndContaUsuarioId(inicio, fim, id);
	}
}
