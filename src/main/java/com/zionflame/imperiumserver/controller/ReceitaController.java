package com.zionflame.imperiumserver.controller;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.zionflame.imperiumserver.controller.dto.MensagemDto;
import com.zionflame.imperiumserver.controller.dto.ReceitaDetalhesDto;
import com.zionflame.imperiumserver.controller.dto.ReceitaDto;
import com.zionflame.imperiumserver.controller.form.ContaIdForm;
import com.zionflame.imperiumserver.controller.form.ReceitaForm;
import com.zionflame.imperiumserver.controller.form.TransacaoFormAtualiza;
import com.zionflame.imperiumserver.controller.form.ValorForm;
import com.zionflame.imperiumserver.helper.DateHelper;
import com.zionflame.imperiumserver.model.Categoria;
import com.zionflame.imperiumserver.model.Conta;
import com.zionflame.imperiumserver.model.Historia;
import com.zionflame.imperiumserver.model.Receita;
import com.zionflame.imperiumserver.model.enums.Natureza;
import com.zionflame.imperiumserver.repository.CategoriaRepository;
import com.zionflame.imperiumserver.repository.ContaRepository;
import com.zionflame.imperiumserver.service.HistoriaService;
import com.zionflame.imperiumserver.service.ReceitaService;

@RestController
@RequestMapping("/receitas")
public class ReceitaController {

	@Autowired
	private ContaRepository contaRepository;

	@Autowired
	private CategoriaRepository categoriaRepository;

	@Autowired
	private ReceitaService receitaService;

	@Autowired
	private HistoriaService historiaService;

	@PostMapping
	public ResponseEntity<?> adicionar(@RequestBody ReceitaForm form, UriComponentsBuilder uriBuilder) {

		Optional<Conta> optConta = contaRepository.findById(form.getContaId());
		if (!optConta.isPresent())
			return ResponseEntity.badRequest().body(new MensagemDto("Conta inválida!"));
		Conta conta = optConta.get();

		Optional<Categoria> optCategoria = categoriaRepository.findById(form.getCategoriaId());

		if (!optCategoria.isPresent())
			return ResponseEntity.badRequest().body(new MensagemDto("Categoria inválida!"));
		Categoria categoria = optCategoria.get();

		if (form.isConcluida())
			conta.soma(form.getValor());

		Receita receita = form.converter();
		receita.setCategoria(categoria);
		receita.setConta(conta);
		receitaService.adicionaReceita(receita);

		historiaService.adiciona(new Historia(receita, Natureza.RECEITA, receita.getConta().getUsuario()));

		URI uri = uriBuilder.path("/receitas/{id}").buildAndExpand(receita.getId()).toUri();
		return ResponseEntity.created(uri).body(new ReceitaDto(receita));
	}

	@Transactional
	@PutMapping("/{id}")
	public ResponseEntity<?> atualizar(@PathVariable Long id, @RequestBody TransacaoFormAtualiza form) {
		Receita receita = receitaService.buscarPorId(id);
		if (receita == null)
			return ResponseEntity.badRequest().body(new MensagemDto("Receita inválida!"));

		Optional<Categoria> categoria = categoriaRepository.findById(form.getCategoriaId());
		if (!categoria.isPresent())
			return ResponseEntity.badRequest().body(new MensagemDto("Categoria inválida!"));

		receita.setDescricao(form.getDescricao());
		receita.setData(form.getData());
		receita.setHora(form.getHora());
		receita.setCategoria(categoria.get());

		historiaService.atualiza(new Historia(receita, Natureza.RECEITA, receita.getConta().getUsuario()));

		return ResponseEntity.ok(new ReceitaDto(receita));
	}

	@Transactional
	@PatchMapping("/{id}/finaliza")
	public ResponseEntity<?> finaliza(@PathVariable Long id) {

		Receita receita = receitaService.buscarPorId(id);
		if (receita == null)
			return ResponseEntity.badRequest().body(new MensagemDto("Receita inválida!"));

		if (receita.isConcluida())
			return ResponseEntity.badRequest().body(new MensagemDto("A receita já foi recebida!"));

		receita.getConta().soma(receita.getValor());
		receita.setConcluida(true);

		return ResponseEntity.ok(new ReceitaDto(receita));
	}

	@Transactional
	@PatchMapping("/{id}/altera/conta")
	public ResponseEntity<?> alteraConta(@PathVariable Long id, @RequestBody ContaIdForm form) {
		Receita receita = receitaService.buscarPorId(id);
		if (receita == null)
			return ResponseEntity.badRequest().body(new MensagemDto("Receita inválida!"));

		Optional<Conta> optConta = contaRepository.findById(form.getContaId());
		if (!optConta.isPresent())
			return ResponseEntity.badRequest().body(new MensagemDto("Conta inválida!"));
		Conta conta = optConta.get();

		if (receita.isConcluida()) {
			if (!receita.getConta().subtrai(receita.getValor()))
				return ResponseEntity.badRequest().body(new MensagemDto("Saldo insuficiente!"));

			conta.soma(receita.getValor());
		}

		receita.setConta(conta);

		return ResponseEntity.ok(new ReceitaDto(receita));
	}

	@Transactional
	@PatchMapping("/{id}/altera/valor")
	public ResponseEntity<?> alteraValor(@PathVariable Long id, @RequestBody ValorForm form) {
		Receita receita = receitaService.buscarPorId(id);
		if (receita == null)
			return ResponseEntity.badRequest().body(new MensagemDto("Receita inválida!"));

		if (receita.isConcluida()) {
			if (!receita.getConta().subtrai(receita.getValor()))
				return ResponseEntity.badRequest().body(new MensagemDto("Saldo insuficiente!"));
			receita.getConta().soma(form.getValor());
		}

		receita.setValor(form.getValor());

		historiaService.atualiza(new Historia(receita, Natureza.RECEITA, receita.getConta().getUsuario()));

		return ResponseEntity.ok(new ReceitaDto(receita));
	}

	@Transactional
	@DeleteMapping("/{id}")
	public ResponseEntity<?> excluir(@PathVariable Long id) {

		Receita receita = receitaService.buscarPorId(id);
		if (receita == null)
			return ResponseEntity.badRequest().body(new MensagemDto("Receita inválida!"));

		if (receita.isConcluida()) {
			if (!receita.getConta().subtrai(receita.getValor()))
				return ResponseEntity.badRequest().body(new MensagemDto("Saldo insuficiente!"));
		}

		receita.setDeletado(true);

		historiaService.exclui(new Historia(receita, Natureza.RECEITA, receita.getConta().getUsuario()));

		return ResponseEntity.ok().build();
	}

	@GetMapping("/usuario/{usuarioId}")
	public ResponseEntity<?> listar(@PathVariable Long usuarioId,

			@PageableDefault(sort = {"data","hora"}, direction = Direction.DESC, page = 0, size = 15) Pageable pageable) {

		Page<Receita> receitas = receitaService.listarPorUsuario(usuarioId, pageable);
		return ResponseEntity.ok(ReceitaDto.converter(receitas));
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> detalhar(@PathVariable Long id) {
		Receita receita = receitaService.buscarPorId(id);
		if (receita == null)
			return ResponseEntity.badRequest().body(new MensagemDto("Receita inválida!"));
		return ResponseEntity.ok(new ReceitaDetalhesDto(receita));
	}

	@GetMapping("/filtra/usuario/{id}/data/{data}")
	public ResponseEntity<?> listarPorData(@PathVariable Long id, @PathVariable String data,
			@PageableDefault(sort = {"data","hora"}, direction = Direction.DESC, page = 0, size = 15) Pageable pageable) {
		Page<Receita> receitas = receitaService.listarPorUsuarioData(id, DateHelper.data(data), pageable);
		return ResponseEntity.ok(ReceitaDto.converter(receitas));
	}

	@GetMapping("/filtra/usuario/{id}/descricao/{descricao}")
	public ResponseEntity<?> listarPorDescricao(@PathVariable Long id, @PathVariable String descricao,
			@PageableDefault(sort = {"data","hora"}, direction = Direction.DESC, page = 0, size = 15) Pageable pageable) {

		Page<Receita> receitas = receitaService.listarPorUsuarioDescricao(id, descricao, pageable);
		return ResponseEntity.ok(ReceitaDto.converter(receitas));
	}

	@GetMapping("/filtra/usuario/{id}/mes/{mes}")
	public ResponseEntity<?> filtrarPorMes(@PathVariable Long id, @PathVariable String mes,
			@PageableDefault(sort = {"data","hora"}, direction = Direction.DESC, page = 0, size = 15) Pageable pageable) {
		LocalDate[] periodo = DateHelper.mes(mes);
		Page<Receita> receitas = receitaService.filtrarPorUsuarioMes(id, periodo[0], periodo[1], pageable);
		return ResponseEntity.ok(ReceitaDto.converter(receitas));
	}

	@GetMapping("/lista/usuario/{id}/mes/{mes}")
	public ResponseEntity<?> filtrarPorMes(@PathVariable Long id, @PathVariable String mes) {
		LocalDate[] periodo = DateHelper.mes(mes);
		List<Receita> receitas = receitaService.listarPorUsuarioMes(id, periodo[0], periodo[1]);
		return ResponseEntity.ok(ReceitaDto.converter(receitas));
	}
}
