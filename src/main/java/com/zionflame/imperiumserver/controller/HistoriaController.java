package com.zionflame.imperiumserver.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zionflame.imperiumserver.config.security.dto.HistoriaDto;
import com.zionflame.imperiumserver.model.Historia;
import com.zionflame.imperiumserver.repository.HistoriaRepository;

@RestController
@RequestMapping("/historias")
public class HistoriaController {

	@Autowired
	private HistoriaRepository historiaRepository;

	@GetMapping("/usuario/{usuarioId}")
	public ResponseEntity<?> listarPorUsuario(@PathVariable Long usuarioId,
			@PageableDefault(sort = {"data", "hora"}, direction = Direction.DESC, page = 0, size = 15) Pageable pageable) {
		
		Page<Historia> historias = historiaRepository.findByUsuarioId(usuarioId, pageable);
		
		return ResponseEntity.ok(HistoriaDto.converter(historias));
	}
}
