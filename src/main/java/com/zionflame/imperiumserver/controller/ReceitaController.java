
package com.zionflame.imperiumserver.controller;

import java.math.BigDecimal;
import java.net.URI;
import java.time.LocalDate;
import java.util.List;

import javax.transaction.Transactional;

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
import com.zionflame.imperiumserver.controller.dto.MensagemDto;
import com.zionflame.imperiumserver.controller.dto.ReceitaDetalhesDto;
import com.zionflame.imperiumserver.controller.dto.ReceitaDto;
import com.zionflame.imperiumserver.controller.form.ReceitaForm;
import com.zionflame.imperiumserver.controller.form.TransacaoFormAtualiza;
import com.zionflame.imperiumserver.event.model.AtualizaReceitaEvent;
import com.zionflame.imperiumserver.event.model.NovaReceitaEvent;
import com.zionflame.imperiumserver.helper.ConstantsHelper;
import com.zionflame.imperiumserver.helper.DateHelper;
import com.zionflame.imperiumserver.model.Categoria;
import com.zionflame.imperiumserver.model.Conta;
import com.zionflame.imperiumserver.model.Historia;
import com.zionflame.imperiumserver.model.Receita;
import com.zionflame.imperiumserver.model.Usuario;
import com.zionflame.imperiumserver.model.enums.Natureza;
import com.zionflame.imperiumserver.repository.CategoriaRepository;
import com.zionflame.imperiumserver.repository.ContaRepository;
import com.zionflame.imperiumserver.repository.ReceitaRepository;
import com.zionflame.imperiumserver.repository.specification.ReceitaSpecification;
import com.zionflame.imperiumserver.service.HistoriaService;
import com.zionflame.imperiumserver.service.ReceitaService;

@RestController
@RequestMapping("/receitas")
public class ReceitaController implements ConstantsHelper {

	@Autowired
	private ContaRepository contaRepository;

	@Autowired
	private CategoriaRepository categoriaRepository;

	@Autowired
	private ReceitaService receitaService;

	@Autowired
	private HistoriaService historiaService;

	@Autowired
	private ReceitaRepository receitaRepository;

	@Autowired
	private ApplicationEventPublisher publisher;
	
	@GetMapping("/page")
	public ResponseEntity<?> listar(@RequestAttribute(USUARIO_ATT_REQ) Usuario usuario,
			@RequestParam(required = false) String ano, 
			@RequestParam(required = false) String mes,
			@RequestParam(required = false) String data, 
			@RequestParam(required = false) BigDecimal valor,
			@RequestParam(required = false) String descricao,
			@RequestParam(required = false) Long categoriaId,
			@RequestParam(required = false) Long contaId,
			@PageableDefault(sort = "data", direction = Direction.DESC, page = 0, size = 15) Pageable pageable) {

		return ResponseEntity.ok(ReceitaDto.converter(receitaRepository.findAll(Specification.where(

				ReceitaSpecification.contaIdAndcontaUsuarioEqual(contaId ,usuario))
				.and(ReceitaSpecification.dataEqual(data))
				.and(ReceitaSpecification.dataMensalEqual(mes))
				.and(ReceitaSpecification.descricaoLike(descricao))
				.and(ReceitaSpecification.valorEqual(valor))
				.and(ReceitaSpecification.categoriaIdEqual(categoriaId))
				,pageable)));
	}

	@PostMapping
	public ResponseEntity<?> adicionar(@RequestAttribute(USUARIO_ATT_REQ) Usuario usuario,
			@RequestBody ReceitaForm form, UriComponentsBuilder uriBuilder) {

		Conta conta = contaRepository.findByIdAndUsuario(form.getContaId(), usuario)
				.orElseThrow(() -> new BadRequestException("Conta inválida"));

		Categoria categoria = categoriaRepository.findById(form.getCategoriaId())
				.orElseThrow(() -> new BadRequestException("Categoria inválida!"));

		conta.soma(form.getValor());

		Receita receita = form.converter();
		receita.setCategoria(categoria);
		receita.setConta(conta);
		receitaService.adicionaReceita(receita);

		publisher.publishEvent(new NovaReceitaEvent(receita.getId(), usuario));

		URI uri = uriBuilder.path("/receitas/{id}").buildAndExpand(receita.getId()).toUri();
		return ResponseEntity.created(uri).body(new ReceitaDto(receita));
	}

	@Transactional
	@PutMapping("/{id}")
	public ResponseEntity<?> atualizar(@RequestAttribute(USUARIO_ATT_REQ) Usuario usuario, @PathVariable Long id,
			@RequestBody TransacaoFormAtualiza form) {
		Receita receita = receitaRepository.findById(id).orElseThrow(() -> new BadRequestException("Receita inválida"));

		Categoria categoria = categoriaRepository.findById(form.getCategoriaId())
				.orElseThrow(() -> new BadRequestException("Categoria inválida!"));

		Conta conta = contaRepository.findByIdAndUsuario(form.getContaId(), usuario)
				.orElseThrow(() -> new BadRequestException("Conta inválida"));

		receita.setDescricao(form.getDescricao());
		receita.setData(form.getData());
		receita.setHora(form.getHora());
		receita.setCategoria(categoria);

		receita.getConta().subtrai(receita.getValor());
		receita.getConta().soma(form.getValor());
		receita.setValor(form.getValor());

		receita.getConta().subtrai(receita.getValor());
		conta.soma(receita.getValor());
		receita.setConta(conta);

		publisher.publishEvent(new AtualizaReceitaEvent(receita.getId(), usuario));

		return ResponseEntity.ok(new ReceitaDto(receita));
	}

	@Transactional
	@DeleteMapping("/{id}")
	public ResponseEntity<?> excluir(@PathVariable Long id) {

		Receita receita = receitaService.buscarPorId(id);
		if (receita == null)
			return ResponseEntity.badRequest().body(new MensagemDto("Receita inválida!"));

		if (receita.isConcluida()) {
			receita.getConta().subtrai(receita.getValor());
		}

		receita.setDeletado(true);

		historiaService
				.exclui(new Historia(receita, Natureza.RECEITA, receita.getConta().getUsuario(), receita.getConta()));

		return ResponseEntity.ok().build();
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> detalhar(@PathVariable Long id) {
		return ResponseEntity.ok(new ReceitaDetalhesDto(
				receitaRepository.findById(id).orElseThrow(() -> new BadRequestException("Receita inválida"))));
	}

	@GetMapping("/filtra/usuario/{id}/data/{data}")
	public ResponseEntity<?> listarPorData(@PathVariable Long id, @PathVariable String data,
			@PageableDefault(sort = "data", direction = Direction.DESC, page = 0, size = 15) Pageable pageable) {
		Page<Receita> receitas = receitaService.listarPorUsuarioData(id, DateHelper.data(data), pageable);
		return ResponseEntity.ok(ReceitaDto.converter(receitas));
	}

	@GetMapping("/filtra/usuario/{id}/descricao/{descricao}")
	public ResponseEntity<?> listarPorDescricao(@PathVariable Long id, @PathVariable String descricao,
			@PageableDefault(sort = "data", direction = Direction.DESC, page = 0, size = 15) Pageable pageable) {

		Page<Receita> receitas = receitaService.listarPorUsuarioDescricao(id, descricao, pageable);
		return ResponseEntity.ok(ReceitaDto.converter(receitas));
	}

	@GetMapping("/filtra/usuario/{id}/mes/{mes}")
	public ResponseEntity<?> filtrarPorMes(@PathVariable Long id, @PathVariable String mes,
			@PageableDefault(sort = "data", direction = Direction.DESC, page = 0, size = 15) Pageable pageable) {
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
