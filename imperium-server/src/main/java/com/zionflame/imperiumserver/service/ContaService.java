package com.zionflame.imperiumserver.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zionflame.imperiumserver.controller.form.ContaForm;
import com.zionflame.imperiumserver.controller.form.ContaFormAtualiza;
import com.zionflame.imperiumserver.model.Conta;
import com.zionflame.imperiumserver.model.TipoConta;
import com.zionflame.imperiumserver.repository.ContaRepository;
import com.zionflame.imperiumserver.repository.TipoContaRepository;
import com.zionflame.imperiumserver.repository.UsuarioRepository;

@Service
public class ContaService {

	@Autowired
	private ContaRepository contaRepository;

	@Autowired
	private TipoContaRepository tipoContaRepository;

	@Autowired
	private UsuarioRepository usuarioRepository;

	public Conta adicionarConta(ContaForm form) {

		Conta conta = form.converter(tipoContaRepository, usuarioRepository);
		if (conta == null) {
			return null;
		}
		return contaRepository.save(conta);
	}

	public Conta atualizaConta(Long id, ContaFormAtualiza form) {
		Conta conta = this.buscarPorId(id);
		if (conta == null)
			return null;
		Optional<TipoConta> tipoConta = tipoContaRepository.findById(form.getTipoContaId());
		if (!tipoConta.isPresent())
			return null;
		conta.setNome(form.getNome());
		conta.setDescricao(form.getDescricao());
		conta.setIncluiSoma(form.isIncluiSoma());
		conta.setAtivo(form.isAtivo());
		conta.setTipo(tipoConta.get());
		return conta;
	}

	public Conta buscarPorId(Long id) {
		Optional<Conta> optional = contaRepository.findById(id);
		if (!optional.isPresent())
			return null;
		return optional.get();
	}

	public Conta alteraSaldo(Long id, BigDecimal saldo) {
		Conta conta = this.buscarPorId(id);
		if (conta == null)
			return null;
		conta.setSaldo(saldo);
		return conta;
	}

	public List<Conta> listarPorUsuario(Long usuarioId) {
		return contaRepository.findByUsuarioId(usuarioId);
	}
}
