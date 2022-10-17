package com.zionflame.imperiumserver.controller;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zionflame.imperiumserver.config.exeption.BadRequestException;
import com.zionflame.imperiumserver.controller.dto.MensagemDto;
import com.zionflame.imperiumserver.controller.form.AcaoEmprestimoDto;
import com.zionflame.imperiumserver.controller.form.AcaoEmprestimoForm;
import com.zionflame.imperiumserver.helper.ConstantsHelper;
import com.zionflame.imperiumserver.model.AcaoEmprestimo;
import com.zionflame.imperiumserver.model.Conta;
import com.zionflame.imperiumserver.model.Emprestimo;
import com.zionflame.imperiumserver.model.Usuario;
import com.zionflame.imperiumserver.model.enums.NaturezaEmprestimo;
import com.zionflame.imperiumserver.repository.ContaRepository;
import com.zionflame.imperiumserver.service.AcaoEmprestimoService;
import com.zionflame.imperiumserver.service.EmprestimoService;

@RestController
@RequestMapping("/acoes/emprestimo")
public class AcaoEmprestimoController implements ConstantsHelper {

	@Autowired
	private AcaoEmprestimoService acaoService;

	@Autowired
	private ContaRepository contaRepository;

	@Autowired
	private EmprestimoService emprestimoService;

	@PostMapping
	public ResponseEntity<?> adicionar(@RequestAttribute(USUARIO_ATT_REQ) Usuario usuario,
			@RequestBody AcaoEmprestimoForm form) {

		Conta conta = contaRepository.findByIdAndUsuario(form.getContaId(), usuario)
				.orElseThrow(() -> new BadRequestException("Conta inválida"));
		
		Emprestimo emprestimo = emprestimoService.buscarPorId(form.getEmprestimoId());
		
		if (emprestimo == null)
			return ResponseEntity.badRequest().body(new MensagemDto("Emprestimo inválido!"));

		if (emprestimo.getNatureza() == NaturezaEmprestimo.TOMADO) {
			conta.subtrai(form.getValor());
		} else {
			conta.soma(form.getValor());
		}

		AcaoEmprestimo acao = form.converter();
		acao.setConta(conta);
		acao.setEmprestimo(emprestimo);

		acaoService.adicionaAcao(acao);
		return ResponseEntity.ok(new AcaoEmprestimoDto(acao));
	}

	@Transactional
	@DeleteMapping("/{id}")
	public ResponseEntity<?> excluir(@PathVariable Long id) {
		AcaoEmprestimo acao = acaoService.buscarPorId(id);
		if (acao == null)
			return ResponseEntity.badRequest().body(new MensagemDto("Ação de emprestimo inválida!"));

		if (acao.getEmprestimo().getNatureza() == NaturezaEmprestimo.CEDIDO) {
			acao.getConta().subtrai(acao.getValor());
		} else {
			acao.getConta().soma(acao.getValor());
		}

		acaoService.excluir(acao);
		return ResponseEntity.ok().build();
	}
}
