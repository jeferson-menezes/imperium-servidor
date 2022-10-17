package com.zionflame.imperiumserver.controller;

import java.net.URI;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.zionflame.imperiumserver.controller.dto.MensagemDto;
import com.zionflame.imperiumserver.controller.dto.TransferenciaDto;
import com.zionflame.imperiumserver.controller.form.TransferenciaForm;
import com.zionflame.imperiumserver.model.Conta;
import com.zionflame.imperiumserver.model.Historia;
import com.zionflame.imperiumserver.model.Transferencia;
import com.zionflame.imperiumserver.model.enums.Natureza;
import com.zionflame.imperiumserver.repository.ContaRepository;
import com.zionflame.imperiumserver.repository.TransferenciaRepository;
import com.zionflame.imperiumserver.service.HistoriaService;

@RestController
@RequestMapping("/transferencias")
public class TransferenciaController {

	@Autowired
	private ContaRepository contaRepository;

	@Autowired
	private TransferenciaRepository transferenciaRepository;
	

	@Autowired
	private HistoriaService historiaService;

	@Transactional
	@PostMapping
	public ResponseEntity<?> transfere(@RequestBody TransferenciaForm form, UriComponentsBuilder uriBuilder) {
		Optional<Conta> origem = contaRepository.findById(form.getContaOrigemId());
		Optional<Conta> destino = contaRepository.findById(form.getContaDestinoId());

		if (!origem.isPresent() || !destino.isPresent())
			return ResponseEntity.badRequest().body(new MensagemDto("Conta inválida!"));

		Conta contaOrigem = origem.get();
		Conta contaDestino = destino.get();

		contaOrigem.subtrai(form.getValor());

		contaDestino.soma(form.getValor());

		Transferencia trasferencia = form.converter();
		trasferencia.setContaOrigem(contaOrigem);
		trasferencia.setContaDestino(contaDestino);

		transferenciaRepository.save(trasferencia);

		historiaService.adiciona(new Historia(trasferencia, Natureza.TRANSFERENCIA, contaDestino.getUsuario(), contaOrigem));
		
		URI uri = uriBuilder.path("/transferencias/{id}").buildAndExpand(trasferencia.getId()).toUri();
		return ResponseEntity.created(uri).body(new TransferenciaDto(trasferencia));
	}
	
	@GetMapping("/usuario/{usuarioId}")
	public ResponseEntity<?> listarPorUsuario(@PathVariable Long usuarioId,
			@PageableDefault(sort = "data", direction = Direction.DESC, page = 0, size = 15) Pageable pageable) {
		
		Page<Transferencia> transferencias = transferenciaRepository.findByContaOrigemUsuarioId(usuarioId, pageable);
		
		return ResponseEntity.ok(TransferenciaDto.converter(transferencias));
	}
}
