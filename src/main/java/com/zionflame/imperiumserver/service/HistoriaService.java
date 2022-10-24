package com.zionflame.imperiumserver.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zionflame.imperiumserver.config.exeption.BadRequestException;
import com.zionflame.imperiumserver.model.Despesa;
import com.zionflame.imperiumserver.model.Historia;
import com.zionflame.imperiumserver.model.Receita;
import com.zionflame.imperiumserver.model.Transferencia;
import com.zionflame.imperiumserver.model.Usuario;
import com.zionflame.imperiumserver.model.enums.Natureza;
import com.zionflame.imperiumserver.repository.DespesaRepository;
import com.zionflame.imperiumserver.repository.HistoriaRepository;
import com.zionflame.imperiumserver.repository.ReceitaRepository;
import com.zionflame.imperiumserver.repository.TransferenciaRepository;

@Service
public class HistoriaService {

	@Autowired
	private HistoriaRepository historiaRepository;

	@Autowired
	private TransferenciaRepository transferenciaRepository;

	@Autowired
	private DespesaRepository despesaRepository;

	@Autowired
	private ReceitaRepository receitaRepository;


	public void exclui(Historia historia) {
		Optional<Historia> optional = historiaRepository.findByNaturezaAndTransacaoIdAndUsuarioId(
				historia.getNatureza(), historia.getTransacaoId(), historia.getUsuario().getId());

		if (!optional.isPresent())
			return;

		Historia h = optional.get();
		historiaRepository.delete(h);

	}

	public void adicionaTransferencia(Long transferenciaId, Usuario usuario) {

		Transferencia transferencia = transferenciaRepository.findById(transferenciaId)
				.orElseThrow(() -> new BadRequestException("Transferência inválida!"));
		historiaRepository
				.save(new Historia(transferencia, Natureza.TRANSFERENCIA, usuario, transferencia.getContaOrigem(), transferencia.getContaDestino()));
	}

	public void adicionaDespesa(Long despesaId, Usuario usuario) {

		Despesa despesa = despesaRepository.findById(despesaId)
				.orElseThrow(() -> new BadRequestException("Despesa inválida!"));
		historiaRepository.save(new Historia(despesa, Natureza.DESPESA, usuario, despesa.getConta()));
	}

	public void adicionaReceita(Long receitaId, Usuario usuario) {

		Receita receita = receitaRepository.findById(receitaId)
				.orElseThrow(() -> new BadRequestException("Receita inválida!"));
		historiaRepository.save(new Historia(receita, Natureza.RECEITA, usuario, receita.getConta()));
	}

	public void atualizaDespesa(Long despesaId, Usuario usuario) {

		Despesa despesa = despesaRepository.findById(despesaId)
				.orElseThrow(() -> new BadRequestException("Despesa inválida!"));

		Historia historia = historiaRepository
				.findByNaturezaAndTransacaoIdAndUsuario(Natureza.DESPESA, despesaId, usuario)
				.orElseThrow(() -> new BadRequestException("Despesa inválida!"));

		historia.setData(despesa.getData());
		historia.setHora(despesa.getHora());
		historia.setDescricao(despesa.getDescricao());
		historia.setValor(despesa.getValor());

		historiaRepository.save(historia);
	}

	public void atualizaReceita(Long receitaId, Usuario usuario) {

		Receita receita = receitaRepository.findById(receitaId)
				.orElseThrow(() -> new BadRequestException("Receita inválida!"));

		Historia historia = historiaRepository
				.findByNaturezaAndTransacaoIdAndUsuario(Natureza.RECEITA, receitaId, usuario)
				.orElseThrow(() -> new BadRequestException("Historia inválida!"));

		historia.setData(receita.getData());
		historia.setHora(receita.getHora());
		historia.setDescricao(receita.getDescricao());
		historia.setValor(receita.getValor());

		historiaRepository.save(historia);
	}
}
