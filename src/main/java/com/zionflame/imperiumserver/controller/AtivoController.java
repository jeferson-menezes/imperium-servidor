package com.zionflame.imperiumserver.controller;

import java.net.URI;
import java.util.Objects;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.zionflame.imperiumserver.config.exeption.BadRequestException;
import com.zionflame.imperiumserver.controller.dto.AtivoDto;
import com.zionflame.imperiumserver.controller.dto.TipoAtivoDto;
import com.zionflame.imperiumserver.controller.form.AtivoForm;
import com.zionflame.imperiumserver.controller.form.AtivoImagemForm;
import com.zionflame.imperiumserver.model.Ativo;
import com.zionflame.imperiumserver.model.Imagem;
import com.zionflame.imperiumserver.model.Setor;
import com.zionflame.imperiumserver.model.TipoAtivo;
import com.zionflame.imperiumserver.repository.AtivoRepository;
import com.zionflame.imperiumserver.repository.ImagemRepository;
import com.zionflame.imperiumserver.repository.SetorRepository;
import com.zionflame.imperiumserver.repository.TipoAtivoRepository;
import com.zionflame.imperiumserver.service.ImagemService;

@RestController
@RequestMapping("/ativos")
public class AtivoController {

	@Autowired
	private TipoAtivoRepository tipoAtivoRepository;

	@Autowired
	private SetorRepository setorRepository;

	@Autowired
	private ImagemRepository imagemRepository;

	@Autowired
	private AtivoRepository ativoRepository;

	@Autowired
	private ImagemService imagemService;

	@PostMapping
	public ResponseEntity<?> adicionar(@RequestBody @Valid AtivoForm form, UriComponentsBuilder uriBuilder) {

		Ativo ativo = ativoRepository.save(form.converter(setorRepository, tipoAtivoRepository));

		URI uri = uriBuilder.path("/cartoes/{id}").buildAndExpand(ativo.getId()).toUri();

		return ResponseEntity.created(uri).body(new AtivoDto(ativo));
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> detalhar(@PathVariable Long id) {
		return ResponseEntity.ok(new AtivoDto(
				ativoRepository.findById(id).orElseThrow(() -> new BadRequestException("Ativo inválido!"))));
	}

	@PutMapping("/{id}")
	public ResponseEntity<?> atualizar(@PathVariable Long id, @RequestBody @Valid AtivoForm form) {

		Ativo ativo = ativoRepository.findById(id).orElseThrow(() -> new BadRequestException("Ativo inválido!"));

		Setor setor = setorRepository.findById(form.getSetorId())
				.orElseThrow(() -> new BadRequestException("Setor inválido!"));

		TipoAtivo tipoAtivo = tipoAtivoRepository.findById(form.getTipoAtivoId())
				.orElseThrow(() -> new BadRequestException("Tipo ativo inválido!"));

		ativo.setCodigo(form.getCodigo());
		ativo.setTipoAtivo(tipoAtivo);
		ativo.setNome(form.getNome());
		ativo.setSetor(setor);

		return ResponseEntity.ok(new AtivoDto(ativoRepository.save(ativo)));
	}

	@PatchMapping("/{id}/imagem")
	public ResponseEntity<?> atualizarImagem(@PathVariable Long id, @RequestBody @Valid AtivoImagemForm form) {

		Ativo ativo = ativoRepository.findById(id).orElseThrow(() -> new BadRequestException("Ativo inválido!"));

		Imagem jaExisteImagem = ativo.getImagem();

		if (jaExisteImagem != null && Objects.equals(form.getImagemId(), ativo.getImagem().getId())) {
			throw new BadRequestException("A imagem é a mesma!");
		}

		Imagem imagem = imagemRepository.findById(form.getImagemId())
				.orElseThrow(() -> new BadRequestException("imagem inválida!"));

		ativo.setImagem(imagem);
		ativoRepository.save(ativo);

		if (jaExisteImagem != null) {
			imagemService.marcarParaExcluir(jaExisteImagem);
		}

		return ResponseEntity.ok(new AtivoDto(ativo));
	}

	@Transactional
	@DeleteMapping("/{id}")
	public ResponseEntity<?> excluir(@PathVariable Long id) {
		Ativo ativo = ativoRepository.findById(id).orElseThrow(() -> new BadRequestException("Ativo inválido!"));
		ativoRepository.delete(ativo);
		return ResponseEntity.ok().build();
	}

	@GetMapping
	public ResponseEntity<?> listar() {
		return ResponseEntity.ok(AtivoDto.converter(ativoRepository.findAll()));
	}

	@GetMapping("/tipos")
	public ResponseEntity<?> listarTipos() {
		return ResponseEntity.ok(TipoAtivoDto.converter(tipoAtivoRepository.findAll()));
	}

}
