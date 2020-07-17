package com.zionflame.imperiumserver.controller;

import java.net.URI;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.zionflame.imperiumserver.controller.dto.MensagemDto;
import com.zionflame.imperiumserver.controller.form.EmprestimoAtualizaForm;
import com.zionflame.imperiumserver.controller.form.EmprestimoDto;
import com.zionflame.imperiumserver.controller.form.EmprestimoForm;
import com.zionflame.imperiumserver.model.Conta;
import com.zionflame.imperiumserver.model.Emprestimo;
import com.zionflame.imperiumserver.model.Usuario;
import com.zionflame.imperiumserver.model.enums.NaturezaEmprestimo;
import com.zionflame.imperiumserver.repository.ContaRepository;
import com.zionflame.imperiumserver.repository.UsuarioRepository;
import com.zionflame.imperiumserver.service.EmprestimoService;

@RestController
@RequestMapping("/emprestimos")
public class EmprestimoController {

	@Autowired
	private EmprestimoService emprestimoService;

	@Autowired
	private ContaRepository contaRepository;

	@Autowired
	private UsuarioRepository usuarioRepository;

	@PostMapping
	public ResponseEntity<?> adicionar(@RequestBody EmprestimoForm form, UriComponentsBuilder uriBuilder) {

		Optional<Usuario> optUsuario = usuarioRepository.findById(form.getUsuarioId());
		if (!optUsuario.isPresent())
			return ResponseEntity.badRequest().body(new MensagemDto("Usuário inválido!"));
		Usuario usuario = optUsuario.get();

		Optional<Conta> optConta = contaRepository.findById(form.getContaId());
		if (!optConta.isPresent())
			return ResponseEntity.badRequest().body(new MensagemDto("Conta inválida!"));
		Conta conta = optConta.get();

		if (form.getNatureza() == NaturezaEmprestimo.TOMADO) {
			conta.soma(form.getValor());
		} else {
			if (!conta.subtrai(form.getValor())) {
				return ResponseEntity.badRequest().body(new MensagemDto("Saldo insuficiente!"));
			}
		}

		Emprestimo emprestimo = form.converter();
		emprestimo.setUsuario(usuario);
		emprestimo.setConta(conta);

		emprestimoService.adicionaEmprestimo(emprestimo);

		URI uri = uriBuilder.path("/emprestimos/{id}").buildAndExpand(emprestimo.getId()).toUri();
		return ResponseEntity.created(uri).body(new EmprestimoDto(emprestimo));
	}

	@PutMapping("/{id}")
	public ResponseEntity<?> atualizar(@PathVariable Long id, @RequestBody EmprestimoAtualizaForm form) {
		Emprestimo emprestimo = emprestimoService.buscarPorId(id);
		if (emprestimo == null)
			return ResponseEntity.badRequest().body(new MensagemDto("Emprestimo inválido!"));

		emprestimo.setDescricao(form.getDescricao());
		emprestimo.setData(form.getData());
		emprestimo.setHora(form.getHora());
		emprestimo.setPessoa(form.getPessoa());

		return ResponseEntity.ok(new EmprestimoDto(emprestimo));
	}

	@Transactional
	@DeleteMapping("/{id}")
	public ResponseEntity<?> excluir(@PathVariable Long id) {
		Emprestimo emprestimo = emprestimoService.buscarPorId(id);
		if (emprestimo == null)
			return ResponseEntity.badRequest().body(new MensagemDto("Emprestimo inválido!"));

		if (!emprestimo.getAcoes().isEmpty())
			return ResponseEntity.badRequest().body(new MensagemDto("Já houve ações no imprestimo!"));

		if (emprestimo.getNatureza() == NaturezaEmprestimo.CEDIDO) {
			emprestimo.getConta().soma(emprestimo.getValor());
		} else {
			if (!emprestimo.getConta().subtrai(emprestimo.getValor())) {
				return ResponseEntity.badRequest().body(new MensagemDto("Saldo insuficiente!"));
			}
		}
		emprestimo.setDeletado(true);

		return ResponseEntity.ok().build();
	}

}
