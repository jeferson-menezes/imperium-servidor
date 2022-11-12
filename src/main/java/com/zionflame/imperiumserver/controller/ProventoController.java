package com.zionflame.imperiumserver.controller;

import java.net.URI;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.zionflame.imperiumserver.controller.dto.ProventoDto;
import com.zionflame.imperiumserver.controller.form.ProventoForm;
import com.zionflame.imperiumserver.helper.ConstantsHelper;
import com.zionflame.imperiumserver.model.Provento;
import com.zionflame.imperiumserver.model.Usuario;
import com.zionflame.imperiumserver.repository.AtivoRepository;
import com.zionflame.imperiumserver.repository.ProventoRepository;

@RestController
@RequestMapping("/proventos")
public class ProventoController implements ConstantsHelper {
	
	@Autowired
	private ProventoRepository proventoRepository;

	@Autowired
	private AtivoRepository ativoRepository;

	@PostMapping
	public ResponseEntity<?> cadastrar(@RequestAttribute(USUARIO_ATT_REQ) Usuario usuario,
			@RequestBody @Valid ProventoForm form, UriComponentsBuilder uriBuilder) {

		Provento provento = proventoRepository.save(form.converter(ativoRepository));

		URI uri = uriBuilder.path("/proventos/{id}").buildAndExpand(provento.getId()).toUri();
		return ResponseEntity.created(uri).body(new ProventoDto(provento));
	}

}
