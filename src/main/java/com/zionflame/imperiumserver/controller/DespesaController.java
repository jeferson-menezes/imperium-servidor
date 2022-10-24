package com.zionflame.imperiumserver.controller;

import java.math.BigDecimal;
import java.net.URI;
import java.time.LocalDate;
import java.util.List;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.zionflame.imperiumserver.config.exeption.BadRequestException;
import com.zionflame.imperiumserver.controller.dto.DespesaDetalhesDto;
import com.zionflame.imperiumserver.controller.dto.DespesaDto;
import com.zionflame.imperiumserver.controller.dto.MensagemDto;
import com.zionflame.imperiumserver.controller.form.DespesaForm;
import com.zionflame.imperiumserver.controller.form.TransacaoFormAtualiza;
import com.zionflame.imperiumserver.event.model.AtualizaDespesaEvent;
import com.zionflame.imperiumserver.event.model.NovaDespesaEvent;
import com.zionflame.imperiumserver.helper.ConstantsHelper;
import com.zionflame.imperiumserver.helper.DateHelper;
import com.zionflame.imperiumserver.model.Categoria;
import com.zionflame.imperiumserver.model.Conta;
import com.zionflame.imperiumserver.model.Despesa;
import com.zionflame.imperiumserver.model.Historia;
import com.zionflame.imperiumserver.model.Usuario;
import com.zionflame.imperiumserver.model.enums.Natureza;
import com.zionflame.imperiumserver.repository.CategoriaRepository;
import com.zionflame.imperiumserver.repository.ContaRepository;
import com.zionflame.imperiumserver.repository.DespesaRepository;
import com.zionflame.imperiumserver.repository.specification.DespesaSpecification;
import com.zionflame.imperiumserver.service.DespesaService;
import com.zionflame.imperiumserver.service.HistoriaService;

@RestController
@RequestMapping("/despesas")
public class DespesaController implements ConstantsHelper {

	@Autowired
	private ContaRepository contaRepository;

	@Autowired
	private CategoriaRepository categoriaRepository;

	@Autowired
	private DespesaService despesaService;

	@Autowired
	private DespesaRepository despesaRepository;

	@Autowired
	private HistoriaService historiaService;

	@Autowired
	private ApplicationEventPublisher publisher;

	@GetMapping("/page")
	public ResponseEntity<?> listar(@RequestAttribute(USUARIO_ATT_REQ) Usuario usuario,
			@RequestParam(required = false) String ano, @RequestParam(required = false) String mes,
			@RequestParam(required = false) String data, @RequestParam(required = false) BigDecimal valor,
			@RequestParam(required = false) String descricao, @RequestParam(required = false) Long categoriaId,
			@RequestParam(required = false) Long contaId,
			@PageableDefault(sort = "data", direction = Direction.DESC, page = 0, size = 15) Pageable pageable) {

		return ResponseEntity
				.ok(DespesaDto
						.converter(
								despesaRepository
										.findAll(
												Specification
														.where(DespesaSpecification.contaIdAndcontaUsuarioEqual(contaId,
																usuario))
														.and(DespesaSpecification.dataEqual(data))
														.and(DespesaSpecification.dataMensalEqual(mes))
														.and(DespesaSpecification.descricaoLike(descricao))
														.and(DespesaSpecification.valorEqual(valor))
														.and(DespesaSpecification.categoriaIdEqual(categoriaId)),
												pageable)));
	}

	@PostMapping
	public ResponseEntity<?> adicionar(@RequestAttribute(USUARIO_ATT_REQ) Usuario usuario,
			@RequestBody @Valid DespesaForm form, UriComponentsBuilder uriBuilder) {

		Conta conta = contaRepository.findByIdAndUsuario(form.getContaId(), usuario)
				.orElseThrow(() -> new BadRequestException("Conta inválida"));

		Categoria categoria = categoriaRepository.findById(form.getCategoriaId())
				.orElseThrow(() -> new BadRequestException("Categoria inválida!"));

		conta.subtrai(form.getValor());

		Despesa despesa = form.converter();
		despesa.setCategoria(categoria);
		despesa.setConta(conta);
		despesaService.adicionaDespesa(despesa);

		publisher.publishEvent(new NovaDespesaEvent(despesa.getId(), usuario));

		URI uri = uriBuilder.path("/despesas/{id}").buildAndExpand(despesa.getId()).toUri();
		return ResponseEntity.created(uri).body(new DespesaDto(despesa));
	}

	@Transactional
	@PutMapping("/{id}")
	public ResponseEntity<?> atualizar(@RequestAttribute(USUARIO_ATT_REQ) Usuario usuario, @PathVariable Long id,
			@RequestBody TransacaoFormAtualiza form) {

		Despesa despesa = despesaRepository.findById(id).orElseThrow(() -> new BadRequestException("Despesa inválida"));

		Categoria categoria = categoriaRepository.findById(form.getCategoriaId())
				.orElseThrow(() -> new BadRequestException("Categoria inválida!"));

		Conta conta = contaRepository.findByIdAndUsuario(form.getContaId(), usuario)
				.orElseThrow(() -> new BadRequestException("Conta inválida"));

		despesa.setDescricao(form.getDescricao());
		despesa.setData(form.getData());
		despesa.setHora(form.getHora());
		despesa.setCategoria(categoria);

		despesa.getConta().soma(despesa.getValor());
		despesa.getConta().subtrai(form.getValor());
		despesa.setValor(form.getValor());

		despesa.getConta().soma(despesa.getValor());
		conta.subtrai(despesa.getValor());
		despesa.setConta(conta);

		publisher.publishEvent(new AtualizaDespesaEvent(despesa.getId(), usuario));

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
		historiaService
				.exclui(new Historia(despesa, Natureza.DESPESA, despesa.getConta().getUsuario(), despesa.getConta()));
		return ResponseEntity.ok().build();
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> detalhar(@PathVariable Long id) {
		return ResponseEntity.ok(new DespesaDetalhesDto(
				despesaRepository.findById(id).orElseThrow(() -> new BadRequestException("Despesa inválida"))));
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
