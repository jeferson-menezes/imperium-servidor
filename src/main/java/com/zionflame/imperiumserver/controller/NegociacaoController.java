package com.zionflame.imperiumserver.controller;

import java.net.URI;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.zionflame.imperiumserver.controller.dto.NegociacaoDto;
import com.zionflame.imperiumserver.controller.form.NegociacaoForm;
import com.zionflame.imperiumserver.event.model.NovaNegociacaoEvent;
import com.zionflame.imperiumserver.helper.ConstantsHelper;
import com.zionflame.imperiumserver.model.Negociacao;
import com.zionflame.imperiumserver.model.Usuario;
import com.zionflame.imperiumserver.repository.AtivoRepository;
import com.zionflame.imperiumserver.repository.NegociacaoRepository;

@RestController
@RequestMapping("/negociacoes")
public class NegociacaoController implements ConstantsHelper{
	
	@Autowired
	private NegociacaoRepository negociacaoRepository;
	
	@Autowired
	private AtivoRepository ativoRepository; 
	

	@Autowired
	private ApplicationEventPublisher publisher;
	
	@PostMapping
	public ResponseEntity<?> cadastrar(@RequestAttribute(USUARIO_ATT_REQ) Usuario usuario,
			@RequestBody @Valid NegociacaoForm form, UriComponentsBuilder uriBuilder){
		
		Negociacao negociacao = negociacaoRepository.save(form.converter(ativoRepository, usuario));

		
		publisher.publishEvent(new NovaNegociacaoEvent());
		
		URI uri = uriBuilder.path("/despesas/{id}").buildAndExpand(negociacao.getId()).toUri();
		return ResponseEntity.created(uri).body(new NegociacaoDto(negociacao));
	}
}
