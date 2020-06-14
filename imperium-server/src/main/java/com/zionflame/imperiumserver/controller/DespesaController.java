package com.zionflame.imperiumserver.controller;

import java.net.URI;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.zionflame.imperiumserver.controller.dto.DespesaDto;
import com.zionflame.imperiumserver.controller.dto.MensagemDto;
import com.zionflame.imperiumserver.controller.form.ContaIdForm;
import com.zionflame.imperiumserver.controller.form.DespesaForm;
import com.zionflame.imperiumserver.controller.form.TransacaoFormAtualiza;
import com.zionflame.imperiumserver.controller.form.ValorForm;
import com.zionflame.imperiumserver.model.Categoria;
import com.zionflame.imperiumserver.model.Conta;
import com.zionflame.imperiumserver.model.Despesa;
import com.zionflame.imperiumserver.repository.CategoriaRepository;
import com.zionflame.imperiumserver.repository.ContaRepository;
import com.zionflame.imperiumserver.service.DespesaService;

@RestController
@RequestMapping("/despesas")
public class DespesaController {

	@Autowired
	private ContaRepository contaRepository;

	@Autowired
	private CategoriaRepository categoriaRepository;

	@Autowired
	private DespesaService despesaService;

	@PostMapping
	public ResponseEntity<?> adicionar(@RequestBody DespesaForm form, UriComponentsBuilder uriBuilder) {

		Optional<Conta> optConta = contaRepository.findById(form.getContaId());
		if (!optConta.isPresent())
			return ResponseEntity.badRequest().body(new MensagemDto("Conta inválida!"));
		Conta conta = optConta.get();

		Optional<Categoria> optCategoria = categoriaRepository.findById(form.getCategoriaId());

		if (!optCategoria.isPresent())
			return ResponseEntity.badRequest().body(new MensagemDto("Categoria inválida!"));
		Categoria categoria = optCategoria.get();

		if (form.isConcluida())
			if (!conta.subtrai(form.getValor()))
				return ResponseEntity.badRequest().body(new MensagemDto("Saldo insuficiente!"));

		Despesa despesa = form.converter();
		despesa.setCategoria(categoria);
		despesa.setConta(conta);
		despesaService.adicionaDespesa(despesa);

		URI uri = uriBuilder.path("/despesas/{id}").buildAndExpand(despesa.getId()).toUri();
		return ResponseEntity.created(uri).body(new DespesaDto(despesa));
	}

	@Transactional
	@PutMapping("/{id}")
	public ResponseEntity<?> atualizar(@PathVariable Long id, @RequestBody TransacaoFormAtualiza form) {
		Despesa despesa = despesaService.buscarPorId(id);
		if (despesa == null)
			return ResponseEntity.badRequest().body(new MensagemDto("Despesa inválida!"));

		Optional<Categoria> categoria = categoriaRepository.findById(form.getCategoriaId());
		if (!categoria.isPresent())
			return ResponseEntity.badRequest().body(new MensagemDto("Categoria inválida!"));

		despesa.setDescricao(form.getDescricao());
		despesa.setData(form.getData());
		despesa.setHora(form.getHora());
		despesa.setCategoria(categoria.get());

		return ResponseEntity.ok(new DespesaDto(despesa));
	}

	@Transactional
	@PatchMapping("/{id}/finaliza")
	public ResponseEntity<?> finaliza(@PathVariable Long id) {

		Despesa despesa = despesaService.buscarPorId(id);
		if (despesa == null)
			return ResponseEntity.badRequest().body(new MensagemDto("Despesa inválida!"));

		if (despesa.isConcluida())
			return ResponseEntity.badRequest().body(new MensagemDto("A despesa já foi paga!"));

		if (!despesa.getConta().subtrai(despesa.getValor()))
			return ResponseEntity.badRequest().body(new MensagemDto("Saldo insuficiente!"));

		despesa.setConcluida(true);

		return ResponseEntity.ok().build();
	}

	@Transactional
	@PatchMapping("/{id}/altera/conta")
	public ResponseEntity<?> alteraConta(@PathVariable Long id, @RequestBody ContaIdForm form) {
		Despesa despesa = despesaService.buscarPorId(id);
		if (despesa == null)
			return ResponseEntity.badRequest().body(new MensagemDto("Despesa inválida!"));

		Optional<Conta> optConta = contaRepository.findById(form.getContaId());
		if (!optConta.isPresent())
			return ResponseEntity.badRequest().body(new MensagemDto("Conta inválida!"));
		Conta conta = optConta.get();

		if (!conta.subtrai(despesa.getValor()))
			return ResponseEntity.badRequest().body(new MensagemDto("Saldo insuficiente!"));

		despesa.getConta().soma(despesa.getValor());

		despesa.setConta(conta);

		return ResponseEntity.ok(new DespesaDto(despesa));
	}

	@Transactional
	@PatchMapping("/{id}/altera/valor")
	public ResponseEntity<?> alteraValor(@PathVariable Long id, @RequestBody ValorForm form) {
		Despesa despesa = despesaService.buscarPorId(id);
		if (despesa == null)
			return ResponseEntity.badRequest().body(new MensagemDto("Despesa inválida!"));

		despesa.getConta().soma(despesa.getValor());

		if (!despesa.getConta().subtrai(form.getValor()))
			return ResponseEntity.badRequest().body(new MensagemDto("Saldo insuficiente!"));

		despesa.setValor(form.getValor());
		return ResponseEntity.ok(new DespesaDto(despesa));
	}

	@Transactional
	@DeleteMapping("/{id}")
	public ResponseEntity<?> excluir(@PathVariable Long id) {

		Despesa despesa = despesaService.buscarPorId(id);
		if (despesa == null)
			return ResponseEntity.badRequest().body(new MensagemDto("Despesa inválida!"));

		despesa.getConta().soma(despesa.getValor());
		despesa.setExcluido(true);
		return ResponseEntity.ok().build();
	}

}
