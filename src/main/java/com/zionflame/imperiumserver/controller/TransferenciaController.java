package com.zionflame.imperiumserver.controller;

import java.net.URI;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.zionflame.imperiumserver.config.exeption.BadRequestException;
import com.zionflame.imperiumserver.controller.dto.TransferenciaDto;
import com.zionflame.imperiumserver.controller.form.TransferenciaForm;
import com.zionflame.imperiumserver.event.model.NovaTransferenciaEvent;
import com.zionflame.imperiumserver.helper.ConstantsHelper;
import com.zionflame.imperiumserver.model.Conta;
import com.zionflame.imperiumserver.model.Transferencia;
import com.zionflame.imperiumserver.model.Usuario;
import com.zionflame.imperiumserver.repository.ContaRepository;
import com.zionflame.imperiumserver.repository.TransferenciaRepository;

@RestController
@RequestMapping("/transferencias")
public class TransferenciaController implements ConstantsHelper {

	@Autowired
	private ContaRepository contaRepository;

	@Autowired
	private TransferenciaRepository transferenciaRepository;

	@Autowired
	private ApplicationEventPublisher publisher;

	@Transactional
	@PostMapping
	public ResponseEntity<?> transfere(@RequestAttribute(USUARIO_ATT_REQ) Usuario usuario,
			@RequestBody TransferenciaForm form, UriComponentsBuilder uriBuilder) {

		if (form.getContaOrigemId() == form.getContaDestinoId())
			throw new BadRequestException("Contas são as mesmas");

		Conta contaOrigem = contaRepository.findByIdAndUsuario(form.getContaOrigemId(), usuario)
				.orElseThrow(() -> new BadRequestException("Conta inválida"));

		Conta contaDestino = contaRepository.findByIdAndUsuario(form.getContaDestinoId(), usuario)
				.orElseThrow(() -> new BadRequestException("Conta inválida"));

		contaOrigem.subtrai(form.getValor());
		contaDestino.soma(form.getValor());

		Transferencia trasferencia = form.converter();
		trasferencia.setContaOrigem(contaOrigem);
		trasferencia.setContaDestino(contaDestino);

		transferenciaRepository.save(trasferencia);

		publisher.publishEvent(new NovaTransferenciaEvent(trasferencia.getId(), usuario));

		URI uri = uriBuilder.path("/transferencias/{id}").buildAndExpand(trasferencia.getId()).toUri();
		return ResponseEntity.created(uri).body(new TransferenciaDto(trasferencia));
	}

	@GetMapping("/page")
	public ResponseEntity<?> listarPorUsuario(@RequestAttribute(USUARIO_ATT_REQ) Usuario usuario,
			@PageableDefault(sort = "data", direction = Direction.DESC, page = 0, size = 15) Pageable pageable) {
		
		return ResponseEntity
				.ok(TransferenciaDto.converter(transferenciaRepository.findByContaOrigemUsuario(usuario, pageable)));
	}
}
