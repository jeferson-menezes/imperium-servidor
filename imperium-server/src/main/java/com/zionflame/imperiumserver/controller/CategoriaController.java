package com.zionflame.imperiumserver.controller;

import java.net.URI;
import java.util.List;
import java.util.Optional;

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

import com.zionflame.imperiumserver.controller.dto.CategoriaDto;
import com.zionflame.imperiumserver.controller.dto.MensagemDto;
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
	public ResponseEntity<?> adicionar(@RequestBody CategoriaForm form, UriComponentsBuilder uriBuilder) {

		Categoria categoria = form.converter();
		categoriaRepository.save(categoria);
		URI uri = uriBuilder.path("/usuarios/{id}").buildAndExpand(categoria.getId()).toUri();
		return ResponseEntity.created(uri).body(new CategoriaDto(categoria));
	}
	
	@PutMapping
	public ResponseEntity<?> atualizar(@PathVariable Long id, CategoriaForm form){
		Optional<Categoria> optional = categoriaRepository.findById(id);
		if (!optional.isPresent()) {
			return ResponseEntity.badRequest().body(new MensagemDto("Categoria inválida!"));
		}
		Categoria categoria = optional.get();
		categoria.setAtivo(form.isAtivo());
		categoria.setDescricao(form.getDescricao());
		categoria.setNatureza(form.getNatureza());
		categoria.setNome(form.getNome());
		return ResponseEntity.ok(new CategoriaDto(categoria));
	}
	
	@GetMapping("/natureza/{natureza}")
	public ResponseEntity<?> listarPorNatureza(@PathVariable NaturezaCategoria natureza){
		List<Categoria> categorias = categoriaRepository.findByNatureza(natureza);
		return ResponseEntity.ok(CategoriaDto.converter(categorias));
	}
}
