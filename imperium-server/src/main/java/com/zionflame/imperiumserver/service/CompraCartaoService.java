package com.zionflame.imperiumserver.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zionflame.imperiumserver.model.CompraCartao;
import com.zionflame.imperiumserver.repository.CompraCartaoRepository;

@Service
public class CompraCartaoService {

	@Autowired
	private CompraCartaoRepository compraCartaoRepository;

	public CompraCartao adicionarCompraCartao(CompraCartao compraCartao) {
		return compraCartaoRepository.save(compraCartao);
	}

	public CompraCartao buscarPorId(Long id) {
		Optional<CompraCartao> optional = compraCartaoRepository.findById(id);
		if (!optional.isPresent()) {
			return null;
		}
		return optional.get();
	}

	public void excluiCompra(CompraCartao compra) {
		compraCartaoRepository.delete(compra);
	}
}
