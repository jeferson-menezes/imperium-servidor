package com.zionflame.imperiumserver.config.tarefa;

import java.util.List;

import com.zionflame.imperiumserver.config.tarefa.status.FaturaAtualizou;
import com.zionflame.imperiumserver.config.tarefa.status.FaturaEPagaOUVencida;
import com.zionflame.imperiumserver.config.tarefa.status.FaturaJaEAberta;
import com.zionflame.imperiumserver.config.tarefa.status.FaturaJaEFechada;
import com.zionflame.imperiumserver.config.tarefa.status.FaturaJaEFutura;
import com.zionflame.imperiumserver.config.tarefa.status.FaturaJaEVencida;
import com.zionflame.imperiumserver.config.tarefa.status.FaturaNaoAtualizou;
import com.zionflame.imperiumserver.model.Cartao;
import com.zionflame.imperiumserver.model.Fatura;
import com.zionflame.imperiumserver.model.enums.StatusFatura;
import com.zionflame.imperiumserver.repository.CartaoRepository;
import com.zionflame.imperiumserver.repository.FaturaRepository;

public class AtualizaStatusFaturaHandler implements Tarefa {

	private CartaoRepository cartaoRepository;
	private FaturaRepository faturaRepository;

	public AtualizaStatusFaturaHandler(CartaoRepository cartaoRepository, FaturaRepository faturaRepository) {
		this.cartaoRepository = cartaoRepository;

		this.faturaRepository = faturaRepository;
	}

	@Override
	public void executa() {

		List<Cartao> cartoes = cartaoRepository.findAll();

		if (cartoes.isEmpty()) {
			return;
		}

		for (Cartao cartao : cartoes) {

			List<Fatura> faturas = faturaRepository.findByCartaoId(cartao.getId());

			if (faturas.isEmpty()) {
				continue;
			}

			this.atualizaStatusFatura(faturas);
		}
	}

	private void atualizaStatusFatura(List<Fatura> faturas) {

		for (Fatura fatura : faturas) {

			FaturaAtualizou faturaAtualizou = new FaturaEPagaOUVencida(
					new FaturaJaEAberta(
							new FaturaJaEFechada(
									new FaturaJaEFutura(
											new FaturaJaEVencida(
													new FaturaNaoAtualizou())))));

			StatusFatura status = faturaAtualizou.verifica(fatura);

			if (status != fatura.getStatus()) {
				fatura.setStatus(status);
				faturaRepository.save(fatura);
			}
		}
	}
	

}
