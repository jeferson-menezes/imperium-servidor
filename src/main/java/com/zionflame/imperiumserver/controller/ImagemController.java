package com.zionflame.imperiumserver.controller;

import java.io.File;
import java.net.URI;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponentsBuilder;

import com.zionflame.imperiumserver.config.exeption.BadRequestException;
import com.zionflame.imperiumserver.controller.dto.ImagemDto;
import com.zionflame.imperiumserver.helper.ConstantsHelper;
import com.zionflame.imperiumserver.model.Imagem;
import com.zionflame.imperiumserver.model.Usuario;
import com.zionflame.imperiumserver.repository.ImagemRepository;
import com.zionflame.imperiumserver.service.StorageService;

@RestController
@RequestMapping("/imagens")
public class ImagemController implements ConstantsHelper {

	@Autowired
	private StorageService storageService;

	@Autowired
	private ImagemRepository imagemRepository;

	@PostMapping
	public ResponseEntity<?> upload(@RequestAttribute(USUARIO_ATT_REQ) Usuario usuario,
			@RequestParam MultipartFile image, UriComponentsBuilder uriBuilder) {

		File imageUrl = storageService.salvar(image);

		Imagem imagem = imagemRepository.save(new Imagem(image.getName(), image.getContentType(),
				image.getOriginalFilename(), imageUrl.toString(), image.getSize(), usuario));

		URI uri = uriBuilder.path("/imagens/{id}").buildAndExpand(imagem.getId()).toUri();

		return ResponseEntity.created(uri).body(new ImagemDto(imagem));
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> download(@RequestAttribute(USUARIO_ATT_REQ) Usuario usuario, @PathVariable Long id) {

		Imagem imagem = imagemRepository.findByIdAndUsuario(id, usuario)
				.orElseThrow(() -> new BadRequestException("Imagem inválida!"));

		UrlResource resource = storageService.buscar(imagem.getPath());

		return ResponseEntity.ok().header(HttpHeaders.CONTENT_TYPE, imagem.getMimeType())
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment: filename=\"" + imagem.getFileName() + "\"")
				.header(HttpHeaders.CONTENT_LENGTH, imagem.getSize().toString())
				.header(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, HttpHeaders.CONTENT_DISPOSITION)
				.contentLength(imagem.getSize()).body(resource);
	}

	@Transactional
	@DeleteMapping("/{id}")
	public ResponseEntity<?> delete(@RequestAttribute(USUARIO_ATT_REQ) Usuario usuario, @PathVariable Long id) {

		Imagem imagem = imagemRepository.findByIdAndUsuario(id, usuario)
				.orElseThrow(() -> new BadRequestException("Imagem inválida!"));

		storageService.deletar(imagem.getPath());
		
		imagemRepository.delete(imagem);
		
		return ResponseEntity.ok().build();
	}

}
