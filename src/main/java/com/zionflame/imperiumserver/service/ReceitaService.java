package com.zionflame.imperiumserver.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

	public Page<Receita> listarPorUsuario(Long usuarioId, Pageable pageable) {
		return receitaRepository.findByContaUsuarioId(usuarioId, pageable);
	}

	public Page<Receita> listarPorUsuarioData(Long id, LocalDate data, Pageable pageable) {

		return receitaRepository.findByDataAndContaUsuarioId(data, id, pageable);
	}

	public Page<Receita> listarPorUsuarioDescricao(Long id, String descricao, Pageable pageable) {
		return receitaRepository.findByContaUsuarioIdAndDescricaoContaining(id, descricao, pageable);
	}

	public Page<Receita> filtrarPorUsuarioMes(Long id, LocalDate inicio, LocalDate fim, Pageable pageable) {
		return receitaRepository.findByDataBetweenAndContaUsuarioId(inicio, fim, id, pageable);
	}

	public List<Receita> listarPorUsuarioMes(Long id, LocalDate inicio, LocalDate fim) {
		return receitaRepository.findByDataBetweenAndContaUsuarioId(inicio, fim, id);
	}
}
