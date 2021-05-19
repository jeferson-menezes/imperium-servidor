package com.zionflame.imperiumserver.controller;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.zionflame.imperiumserver.controller.dto.CartaoDto;
import com.zionflame.imperiumserver.controller.dto.MensagemDto;
import com.zionflame.imperiumserver.controller.form.CartaoForm;
import com.zionflame.imperiumserver.model.Cartao;
import com.zionflame.imperiumserver.repository.BandeiraRepository;
import com.zionflame.imperiumserver.repository.CartaoRepository;
import com.zionflame.imperiumserver.repository.UsuarioRepository;

@RestController
@RequestMapping("/cartoes")
public class CartaoController {

	@Autowired
	private CartaoRepository cartaoRepository;

	@Autowired
	private BandeiraRepository bandeiraRepository;

	@Autowired
	private UsuarioRepository usuarioRepository;

	@PostMapping
	public ResponseEntity<?> adicionar(@RequestBody CartaoForm form, UriComponentsBuilder uriBuilder) {

		Cartao cartao = form.converter(bandeiraRepository, usuarioRepository);

		cartaoRepository.save(cartao);
		URI uri = uriBuilder.path("/cartoes/{id}").buildAndExpand(cartao.getId()).toUri();

		return ResponseEntity.created(uri).body(new CartaoDto(cartao));
	}

	@GetMapping("/usuario/{id}")
	public ResponseEntity<?> listar(@PathVariable Long id) {

		List<Cartao> cartoes = cartaoRepository.findByUsuarioId(id);

		return ResponseEntity.ok(CartaoDto.converter(cartoes));
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> detalhar(@PathVariable Long id) {
		
		Optional<Cartao> optional = cartaoRepository.findById(id);

		if (!optional.isPresent()) {
			return ResponseEntity.badRequest().body(new MensagemDto("Cartão inválido!"));
		}
		
		return ResponseEntity.ok(new CartaoDto(optional.get()));
	}

}
