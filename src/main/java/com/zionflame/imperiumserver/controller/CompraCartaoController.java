package com.zionflame.imperiumserver.controller;

import java.net.URI;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.zionflame.imperiumserver.controller.dto.CompraCartaoDto;
import com.zionflame.imperiumserver.controller.dto.MensagemDto;
import com.zionflame.imperiumserver.controller.form.CompraCartaoForm;
import com.zionflame.imperiumserver.model.Cartao;
import com.zionflame.imperiumserver.model.Categoria;
import com.zionflame.imperiumserver.model.CompraCartao;
import com.zionflame.imperiumserver.repository.CartaoRepository;
import com.zionflame.imperiumserver.repository.CategoriaRepository;
import com.zionflame.imperiumserver.service.CompraCartaoService;
import com.zionflame.imperiumserver.service.FaturaService;

@RestController
@RequestMapping("/compras/cartao")
public class CompraCartaoController {

	@Autowired
	private CompraCartaoService compraCartaoService;

	@Autowired
	private CartaoRepository cartaoRepository;

	@Autowired
	private CategoriaRepository categoriaRepository;

	@Autowired
	private FaturaService faturaService;

	@Transactional
	@PostMapping
	public ResponseEntity<?> adicionar(@RequestBody CompraCartaoForm form, UriComponentsBuilder uriBuilder) {

		Optional<Cartao> optCartao = cartaoRepository.findById(form.getCartaoId());
		if (!optCartao.isPresent())
			return ResponseEntity.badRequest().body(new MensagemDto("Cartão é inválido!"));
		Cartao cartao = optCartao.get();

		Optional<Categoria> optCategoria = categoriaRepository.findById(form.getCategoriaId());
		if (!optCategoria.isPresent())
			return ResponseEntity.badRequest().body(new MensagemDto("Categoria é inválida!"));
		Categoria categoria = optCategoria.get();

		CompraCartao compraCartao = form.converter();
		compraCartao.setCategoria(categoria);
		compraCartao.setCartao(cartao);
		
		compraCartaoService.adicionarCompraCartao(compraCartao);
		
		faturaService.registraCompra(compraCartao);

		URI uri = uriBuilder.path("/compras/cartao/{id}").buildAndExpand(compraCartao.getId()).toUri();
		return ResponseEntity.created(uri).body(new CompraCartaoDto(compraCartao));
	}
	
	@Transactional
	@DeleteMapping("/{id}")
	public ResponseEntity<?> excluir(@PathVariable Long id){
		CompraCartao compra = compraCartaoService.buscarPorId(id);
		if (compra == null) 
			return ResponseEntity.badRequest().body(new MensagemDto("Compra do cartão é inválida!"));	
		
		compraCartaoService.excluiCompra(compra);
		
		return ResponseEntity.ok().build();
	}
}
