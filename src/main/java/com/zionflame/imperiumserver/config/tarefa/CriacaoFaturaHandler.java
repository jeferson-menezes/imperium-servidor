package com.zionflame.imperiumserver.config.tarefa;

import java.util.List;

import com.zionflame.imperiumserver.model.Cartao;
import com.zionflame.imperiumserver.repository.CartaoRepository;
import com.zionflame.imperiumserver.service.FaturaService;

public class CriacaoFaturaHandler implements Tarefa{

	private CartaoRepository cartaoRepository;
	private FaturaService faturaService;

	public CriacaoFaturaHandler(CartaoRepository cartaoRepository, FaturaService faturaService) {
		this.cartaoRepository = cartaoRepository;
		this.faturaService = faturaService;
	}

	public void executa() {

		List<Cartao> cartoes = cartaoRepository.findAll();

		if (cartoes.isEmpty()) {
			return;
		}

		faturaService.verificaExistenciaFaturas(cartoes);

	}

}
