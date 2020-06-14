package com.zionflame.imperiumserver.controller;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.zionflame.imperiumserver.controller.dto.ContaDto;
import com.zionflame.imperiumserver.controller.dto.MensagemDto;
import com.zionflame.imperiumserver.controller.form.ContaForm;
import com.zionflame.imperiumserver.controller.form.ContaFormAtualiza;
import com.zionflame.imperiumserver.controller.form.SaldoForm;
import com.zionflame.imperiumserver.model.Conta;
import com.zionflame.imperiumserver.service.ContaService;

@RestController
@RequestMapping("/contas")
public class ContaController {

	@Autowired
	private ContaService contaService;

	@PostMapping
	public ResponseEntity<?> adicionar(@RequestBody ContaForm form, UriComponentsBuilder uriBuilder) {
		Conta conta = contaService.adicionarConta(form);
		if (conta == null) {
			return ResponseEntity.badRequest().body(new MensagemDto("Usuário o tipo conta inválido!"));
		}
		URI uri = uriBuilder.path("/usuarios/{id}").buildAndExpand(conta.getId()).toUri();
		return ResponseEntity.created(uri).body(new ContaDto(conta));
	}

	@PutMapping("/{id}")
	public ResponseEntity<?> atualizar(@PathVariable Long id, @RequestBody ContaFormAtualiza form) {
		Conta conta = contaService.atualizaConta(id, form);
		if (conta == null) {
			return ResponseEntity.badRequest().body(new MensagemDto("Conta o tipo conta inválido!"));
		}
		return ResponseEntity.ok(new ContaDto(conta));
	}

	@PatchMapping("/{id}")
	public ResponseEntity<?> alteraSaldo(@PathVariable Long id, @RequestBody SaldoForm form) {
		Conta conta = contaService.alteraSaldo(id, form.getSaldo());
		if (conta == null) {
			return ResponseEntity.badRequest().body(new MensagemDto("Conta inválida!"));
		}
		return ResponseEntity.ok(new ContaDto(conta));
	}

	@GetMapping("/usuario/{id}")
	public ResponseEntity<?> listarPorUsuario(@PathVariable Long usuarioId) {
		List<Conta> contas = contaService.listarPorUsuario(usuarioId);
		return ResponseEntity.ok(ContaDto.converter(contas));
	}
}
