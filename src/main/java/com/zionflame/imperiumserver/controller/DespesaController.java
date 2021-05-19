package com.zionflame.imperiumserver.controller;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;
import javax.validation.Valid;

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

import com.zionflame.imperiumserver.controller.dto.DespesaDetalhesDto;
import com.zionflame.imperiumserver.controller.dto.DespesaDto;
import com.zionflame.imperiumserver.controller.dto.MensagemDto;
import com.zionflame.imperiumserver.controller.form.ContaIdForm;
import com.zionflame.imperiumserver.controller.form.DespesaForm;
import com.zionflame.imperiumserver.controller.form.TransacaoFormAtualiza;
import com.zionflame.imperiumserver.controller.form.ValorForm;
import com.zionflame.imperiumserver.helper.DateHelper;
import com.zionflame.imperiumserver.model.Categoria;
import com.zionflame.imperiumserver.model.Conta;
import com.zionflame.imperiumserver.model.Despesa;
import com.zionflame.imperiumserver.model.Historia;
import com.zionflame.imperiumserver.model.enums.Natureza;
import com.zionflame.imperiumserver.repository.CategoriaRepository;
import com.zionflame.imperiumserver.repository.ContaRepository;
import com.zionflame.imperiumserver.service.DespesaService;
import com.zionflame.imperiumserver.service.HistoriaService;

@RestController
@RequestMapping("/despesas")
public class DespesaController {

	@Autowired
	private ContaRepository contaRepository;

	@Autowired
	private CategoriaRepository categoriaRepository;

	@Autowired
	private DespesaService despesaService;
	
	@Autowired
	private HistoriaService historiaService;

	@PostMapping
	public ResponseEntity<?> adicionar(@RequestBody @Valid DespesaForm form, UriComponentsBuilder uriBuilder) {

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
		
		historiaService.adiciona(new Historia(despesa, Natureza.DESPESA, conta.getUsuario(), conta));

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
		
		
		historiaService.atualiza(new Historia(despesa, Natureza.DESPESA, despesa.getConta().getUsuario(), despesa.getConta()));

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
		
		historiaService.atualiza(new Historia(despesa, Natureza.DESPESA, despesa.getConta().getUsuario(), despesa.getConta()));

		return ResponseEntity.ok(new DespesaDto(despesa));
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

		if (despesa.getConta().getId() == conta.getId())
			return ResponseEntity.badRequest().body(new MensagemDto("A conta deve ser diferente da anterior!"));

		if (despesa.isConcluida()) {
			despesa.getConta().soma(despesa.getValor());

			if (!conta.subtrai(despesa.getValor()))
				return ResponseEntity.badRequest().body(new MensagemDto("Saldo insuficiente!"));
		}

		despesa.setConta(conta);
		
		historiaService.atualiza(new Historia(despesa, Natureza.DESPESA, despesa.getConta().getUsuario(), despesa.getConta()));

		return ResponseEntity.ok(new DespesaDto(despesa));
	}

	@Transactional
	@PatchMapping("/{id}/altera/valor")
	public ResponseEntity<?> alteraValor(@PathVariable Long id, @RequestBody ValorForm form) {
		Despesa despesa = despesaService.buscarPorId(id);
		if (despesa == null)
			return ResponseEntity.badRequest().body(new MensagemDto("Despesa inválida!"));

		if (despesa.isConcluida()) {
			despesa.getConta().soma(despesa.getValor());
			if (!despesa.getConta().subtrai(form.getValor()))
				return ResponseEntity.badRequest().body(new MensagemDto("Saldo insuficiente!"));
		}

		despesa.setValor(form.getValor());
		
		historiaService.atualiza(new Historia(despesa, Natureza.DESPESA, despesa.getConta().getUsuario(), despesa.getConta()));
		
		return ResponseEntity.ok(new DespesaDto(despesa));
	}

	@Transactional
	@DeleteMapping("/{id}")
	public ResponseEntity<?> excluir(@PathVariable Long id) {

		Despesa despesa = despesaService.buscarPorId(id);
		if (despesa == null)
			return ResponseEntity.badRequest().body(new MensagemDto("Despesa inválida!"));

		if (despesa.isConcluida())
			despesa.getConta().soma(despesa.getValor());

		despesa.setDeletado(true);
		historiaService.exclui(new Historia(despesa, Natureza.DESPESA, despesa.getConta().getUsuario(), despesa.getConta()));
		return ResponseEntity.ok().build();
	}

	@GetMapping("/usuario/{usuarioId}")
	public ResponseEntity<?> listar(@PathVariable Long usuarioId,
			@PageableDefault(sort = "data", direction = Direction.DESC, page = 0, size = 15) Pageable pageable) {

		Page<Despesa> despesas = despesaService.listarPorUsuario(usuarioId, pageable);
		return ResponseEntity.ok(DespesaDto.converter(despesas));
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> detalhar(@PathVariable Long id) {
		Despesa despesa = despesaService.buscarPorId(id);
		if (despesa == null)
			return ResponseEntity.badRequest().body(new MensagemDto("Despesa inválida!"));
		return ResponseEntity.ok(new DespesaDetalhesDto(despesa));
	}

	@GetMapping("/filtra/usuario/{id}/data/{data}")
	public ResponseEntity<?> listarPorData(@PathVariable Long id, @PathVariable String data,
			@PageableDefault(sort = "data", direction = Direction.DESC, page = 0, size = 15) Pageable pageable) {
		Page<Despesa> despesas = despesaService.listarPorUsuarioData(id, DateHelper.data(data), pageable);
		return ResponseEntity.ok(DespesaDto.converter(despesas));
	}

	@GetMapping("/filtra/usuario/{id}/descricao/{descricao}")
	public ResponseEntity<?> filtrarPorDescricao(@PathVariable Long id, @PathVariable String descricao,

			@PageableDefault(sort = "data", direction = Direction.DESC, page = 0, size = 15) Pageable pageable) {

		Page<Despesa> despesas = despesaService.listarPorUsuarioDescricao(id, descricao, pageable);
		return ResponseEntity.ok(DespesaDto.converter(despesas));
	}

	@GetMapping("/filtra/usuario/{id}/mes/{mes}")
	public ResponseEntity<?> filtrarPorMes(@PathVariable Long id, @PathVariable String mes,
			@PageableDefault(sort = "data", direction = Direction.DESC, page = 0, size = 15) Pageable pageable) {
		LocalDate[] periodo = DateHelper.mes(mes);
		Page<Despesa> despesas = despesaService.filtrarPorUsuarioMes(id, periodo[0], periodo[1], pageable);
		return ResponseEntity.ok(DespesaDto.converter(despesas));
	}

	@GetMapping("/lista/usuario/{id}/mes/{mes}")
	public ResponseEntity<?> listarPorMes(@PathVariable Long id, @PathVariable String mes) {
		LocalDate[] periodo = DateHelper.mes(mes);
		List<Despesa> despesas = despesaService.listarPorUsuarioMes(id, periodo[0], periodo[1]);
		return ResponseEntity.ok(DespesaDto.converter(despesas));
	}
}
