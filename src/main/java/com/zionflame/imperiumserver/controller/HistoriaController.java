package com.zionflame.imperiumserver.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.zionflame.imperiumserver.config.security.dto.HistoriaDto;
import com.zionflame.imperiumserver.helper.ConstantsHelper;
import com.zionflame.imperiumserver.model.Usuario;
import com.zionflame.imperiumserver.model.enums.Natureza;
import com.zionflame.imperiumserver.repository.HistoriaRepository;
import com.zionflame.imperiumserver.repository.specification.HistoriaSpecification;

@RestController
@RequestMapping("/historias")
public class HistoriaController implements ConstantsHelper {

	@Autowired
	private HistoriaRepository historiaRepository;

	@GetMapping("/page")
	public ResponseEntity<?> listarPorUsuario(@RequestAttribute(USUARIO_ATT_REQ) Usuario usuario,
			@RequestParam(required = false) Natureza natureza, @RequestParam(required = false) Long contaId,
			@PageableDefault(sort = { "data", "hora" }, direction = Direction.DESC, page = 0, size = 15) Pageable pageable) {

		return ResponseEntity.ok(HistoriaDto.converter(historiaRepository.findAll(Specification
				.where(HistoriaSpecification.usuarioEqual(usuario))
				.and(HistoriaSpecification.contaIdEqual(contaId)), pageable)));
	}
}
