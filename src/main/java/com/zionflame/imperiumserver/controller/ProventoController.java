package com.zionflame.imperiumserver.controller;

import java.net.URI;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
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
import com.zionflame.imperiumserver.controller.dto.ProventoDto;
import com.zionflame.imperiumserver.controller.form.ProventoForm;
import com.zionflame.imperiumserver.helper.ConstantsHelper;
import com.zionflame.imperiumserver.model.Ativo;
import com.zionflame.imperiumserver.model.Provento;
import com.zionflame.imperiumserver.model.Usuario;
import com.zionflame.imperiumserver.model.enums.Evento;
import com.zionflame.imperiumserver.repository.AtivoRepository;
import com.zionflame.imperiumserver.repository.ProventoRepository;
import com.zionflame.imperiumserver.repository.specification.ProventoSpecification;

@RestController
@RequestMapping("/proventos")
public class ProventoController implements ConstantsHelper {

	@Autowired
	private AtivoRepository ativoRepository;
	
	@Autowired
	private ProventoRepository proventoRepository;

	@PostMapping
	public ResponseEntity<?> cadastrar(@RequestAttribute(USUARIO_ATT_REQ) Usuario usuario,
			@RequestBody @Valid ProventoForm form, UriComponentsBuilder uriBuilder) {

		Provento provento = proventoRepository.save(form.converter(ativoRepository, usuario));

		URI uri = uriBuilder.path("/proventos/{id}").buildAndExpand(provento.getId()).toUri();
		return ResponseEntity.created(uri).body(new ProventoDto(provento));
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> detalhar(@RequestAttribute(USUARIO_ATT_REQ) Usuario usuario, @PathVariable Long id) {
		return ResponseEntity.ok(new ProventoDto(proventoRepository.findByIdAndUsuario(id, usuario)
				.orElseThrow(() -> new BadRequestException("Provento inválido!"))));
	}

	@PutMapping("/{id}")
	public ResponseEntity<?> atualizar(@RequestAttribute(USUARIO_ATT_REQ) Usuario usuario, @PathVariable Long id,
			@RequestBody @Valid ProventoForm form) {

		Provento provento = proventoRepository.findByIdAndUsuario(id, usuario)
				.orElseThrow(() -> new BadRequestException("Provento inválido!"));

		Ativo ativo = ativoRepository.findById(form.getAtivoId())
				.orElseThrow(() -> new BadRequestException("Ativo inválido!"));

		provento.setAtivo(ativo);
		provento.setEvento(form.getEvento());
		provento.setPagoEm(form.getPagoEm());
		provento.setAprovadoEm(form.getAprovadoEm());
		provento.setQuantidade(form.getQuantidade());
		provento.setValorLiquido(form.getValorLiquido());
		provento.setPrecoUnitario(form.getPrecoUnitario());

		return ResponseEntity.ok(new ProventoDto(proventoRepository.save(provento)));
	}

	@GetMapping("/page")
	public ResponseEntity<?> listar(@RequestAttribute(USUARIO_ATT_REQ) Usuario usuario,
			@RequestParam(required = false) String mes, 
			@RequestParam(required = false) Long ativoId,
			@RequestParam(required = false) Evento evento,
			@PageableDefault(sort = "pagoEm", direction = Direction.DESC, page = 0, size = 15) Pageable pageable) {

		Page<Provento> proventos = proventoRepository.findAll(Specification
				.where(ProventoSpecification.usuarioEqual(usuario))
				.and(ProventoSpecification.ativoIdEqual(ativoId))
				.and(ProventoSpecification.pagoEmMensalEqual(mes))
				.and(ProventoSpecification.eventoEqual(evento)),
				pageable);

		return ResponseEntity.ok(ProventoDto.converter(proventos));
	}
}
