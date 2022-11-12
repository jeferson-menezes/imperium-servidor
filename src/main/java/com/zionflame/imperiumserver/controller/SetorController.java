package com.zionflame.imperiumserver.controller;

import java.net.URI;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.zionflame.imperiumserver.config.exeption.BadRequestException;
import com.zionflame.imperiumserver.controller.dto.SetorDto;
import com.zionflame.imperiumserver.controller.form.SetorForm;
import com.zionflame.imperiumserver.model.Setor;
import com.zionflame.imperiumserver.repository.SetorRepository;

@RestController
@RequestMapping("/setores")
public class SetorController {

	@Autowired
	private SetorRepository setorRepository;

	@PostMapping
	public ResponseEntity<?> adicionar(@RequestBody @Valid SetorForm form, UriComponentsBuilder uriBuilder) {

		Setor setor = setorRepository.save(form.converter());

		URI uri = uriBuilder.path("/setores/{id}").buildAndExpand(setor.getId()).toUri();

		return ResponseEntity.created(uri).body(new SetorDto(setor));
	}

	@GetMapping
	public ResponseEntity<?> listar() {
		return ResponseEntity.ok(SetorDto.converter(setorRepository.findAll()));
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> detalhar(@PathVariable Long id) {
		return ResponseEntity.ok(new SetorDto(
				setorRepository.findById(id).orElseThrow(() -> new BadRequestException("Setor inválido!"))));
	}

	@PutMapping("/{id}")
	public ResponseEntity<?> detalhar(@PathVariable Long id, @RequestBody @Valid SetorForm form) {
		
		Setor setor = setorRepository.findById(id).orElseThrow(() -> new BadRequestException("Setor inválido!"));
		
		setor.setNome(form.getNome());
		
		setorRepository.save(setor);
		
		return ResponseEntity.ok(new SetorDto(setor));
	}

}
