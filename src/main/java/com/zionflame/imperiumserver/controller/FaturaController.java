package com.zionflame.imperiumserver.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zionflame.imperiumserver.controller.dto.FaturaDetalhesDto;
import com.zionflame.imperiumserver.controller.dto.FaturaDto;
import com.zionflame.imperiumserver.controller.dto.ItemFaturaDto;
import com.zionflame.imperiumserver.controller.dto.MensagemDto;
import com.zionflame.imperiumserver.model.Fatura;
import com.zionflame.imperiumserver.model.ItemFatura;
import com.zionflame.imperiumserver.model.enums.StatusFatura;
import com.zionflame.imperiumserver.repository.FaturaRepository;
import com.zionflame.imperiumserver.repository.ItemFaturaRepository;

@RestController
@RequestMapping("/faturas")
public class FaturaController {

	@Autowired
	private FaturaRepository faturaRepository;

	@Autowired
	private ItemFaturaRepository itemFaturaREpository;

	@GetMapping("/cartao/{id}")
	public ResponseEntity<?> listar(@PathVariable Long id) {

		List<Fatura> faturas = faturaRepository.findByCartaoId(id);

		return ResponseEntity.ok(FaturaDto.converter(faturas));

	}

	@GetMapping("/{id}")
	public ResponseEntity<?> detalhar(@PathVariable Long id) {

		Optional<Fatura> optional = faturaRepository.findById(id);

		if (!optional.isPresent())
			return ResponseEntity.badRequest().body(new MensagemDto("Fatura é inválida!"));

		return ResponseEntity.ok(new FaturaDetalhesDto(optional.get()));
	}

	@GetMapping("/totais/cartao/{id}")
	public ResponseEntity<?> totais(@PathVariable Long id) {

		List<ItemFatura> itens = itemFaturaREpository.findByFaturaStatusNotAndCompraCartaoId(StatusFatura.PAGA, id);
		
		return ResponseEntity.ok(ItemFaturaDto.converter(itens));
	}
}
