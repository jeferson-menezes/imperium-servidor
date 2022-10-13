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
import com.zionflame.imperiumserver.controller.dto.ContaDetalhesDto;
import com.zionflame.imperiumserver.controller.dto.ContaDto;
import com.zionflame.imperiumserver.controller.form.ContaForm;
import com.zionflame.imperiumserver.helper.ConstantsHelper;
import com.zionflame.imperiumserver.model.Conta;
import com.zionflame.imperiumserver.model.TipoConta;
import com.zionflame.imperiumserver.model.Usuario;
import com.zionflame.imperiumserver.repository.ContaRepository;
import com.zionflame.imperiumserver.repository.TipoContaRepository;

@RestController
@RequestMapping("/contas")
public class ContaController implements ConstantsHelper {

	@Autowired
	private ContaRepository contaRepository;

	@Autowired
	private TipoContaRepository tipoContaRepository;

	@PostMapping
	public ResponseEntity<?> adicionar(@RequestAttribute(USUARIO_ATT_REQ) Usuario usuario,
			@RequestBody @Valid ContaForm form, UriComponentsBuilder uriBuilder) {

		Conta conta = contaRepository.save(form.converter(tipoContaRepository, usuario));

		URI uri = uriBuilder.path("/usuarios/{id}").buildAndExpand(conta.getId()).toUri();

		return ResponseEntity.created(uri).body(new ContaDto(conta));
	}

	@PutMapping("/{id}")
	public ResponseEntity<?> atualizar(@RequestAttribute(USUARIO_ATT_REQ) Usuario usuario,
			@RequestBody @Valid ContaForm form, @PathVariable Long id) {

		Conta conta = contaRepository.findByIdAndUsuario(id, usuario)
				.orElseThrow(() -> new BadRequestException("Conta inválida"));

		TipoConta tipoConta = tipoContaRepository.findById(form.getTipoContaId())
				.orElseThrow(() -> new BadRequestException("Tipo Conta inválida"));

		conta.setNome(form.getNome());
		conta.setDescricao(form.getDescricao());
		conta.setIncluiSoma(form.getIncluiSoma());
		conta.setAtivo(form.getAtivo());
		conta.setTipo(tipoConta);
		conta.setSaldo(form.getSaldo());

		return ResponseEntity.ok(new ContaDto(contaRepository.save(conta)));
	}

	@GetMapping
	public ResponseEntity<?> listarPorUsuario(@RequestAttribute(USUARIO_ID_ATT_REQ) Long usuarioId) {
		return ResponseEntity.ok(ContaDto.converter(contaRepository.findByUsuarioId(usuarioId)));
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> detalhar(@RequestAttribute(USUARIO_ATT_REQ) Usuario usuario, @PathVariable Long id) {
		Conta conta = contaRepository.findByIdAndUsuario(id, usuario)
				.orElseThrow(() -> new BadRequestException("Conta inválida"));

		return ResponseEntity.ok(new ContaDetalhesDto(conta));
	}

}
