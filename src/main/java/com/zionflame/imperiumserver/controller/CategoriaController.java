package com.zionflame.imperiumserver.controller;

import java.net.URI;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.zionflame.imperiumserver.config.exeption.BadRequestException;
import com.zionflame.imperiumserver.controller.dto.CategoriaDto;
import com.zionflame.imperiumserver.controller.form.CategoriaForm;
import com.zionflame.imperiumserver.model.Categoria;
import com.zionflame.imperiumserver.model.enums.NaturezaCategoria;
import com.zionflame.imperiumserver.repository.CategoriaRepository;

@RestController
@RequestMapping("/categorias")
public class CategoriaController {

	@Autowired
	private CategoriaRepository categoriaRepository;

	@PostMapping
	public ResponseEntity<?> adicionar(@RequestBody @Valid CategoriaForm form, UriComponentsBuilder uriBuilder) {

		Categoria categoria = categoriaRepository.save(form.converter());

		URI uri = uriBuilder.path("/categorias/{id}").buildAndExpand(categoria.getId()).toUri();

		return ResponseEntity.created(uri).body(new CategoriaDto(categoria));
	}

	@PutMapping("/{id}")
	public ResponseEntity<?> atualizar(@PathVariable Long id, @RequestBody @Valid CategoriaForm form) {

		Categoria categoria = categoriaRepository.findById(id)
				.orElseThrow(() -> new BadRequestException("Categoria inválida!"));

		categoria.setAtivo(form.getAtivo());
		categoria.setDescricao(form.getDescricao());
		categoria.setNatureza(form.getNatureza());
		categoria.setNome(form.getNome());
		categoria.setCor(form.getCor());
		categoria.setIcone(form.getIcone());

		return ResponseEntity.ok(new CategoriaDto(categoriaRepository.save(categoria)));
	}

	@GetMapping("/natureza/{natureza}")
	public ResponseEntity<?> listarPorNatureza(@PathVariable NaturezaCategoria natureza) {
		return ResponseEntity.ok(CategoriaDto.converter(categoriaRepository.findByNatureza(natureza)));
	}

	@GetMapping
	public ResponseEntity<?> listar() {
		return ResponseEntity.ok(CategoriaDto.converter(categoriaRepository.findAll()));
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> detalhar(@PathVariable Long id) {
		return ResponseEntity.ok(new CategoriaDto(
				categoriaRepository.findById(id).orElseThrow(() -> new BadRequestException("Categoria inválida!"))));
	}

}
