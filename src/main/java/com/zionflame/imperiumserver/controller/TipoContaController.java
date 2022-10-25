package com.zionflame.imperiumserver.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zionflame.imperiumserver.controller.dto.TipoContaDto;
import com.zionflame.imperiumserver.repository.TipoContaRepository;

@RestController
@RequestMapping("/tipos/conta")
public class TipoContaController {

	@Autowired
	private TipoContaRepository tipoContaRepository;

	@GetMapping
	public List<TipoContaDto> listarTodas() {
		return TipoContaDto.converter(tipoContaRepository.findAll());
	}
}
