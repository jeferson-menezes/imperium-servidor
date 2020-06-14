package com.zionflame.imperiumserver.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zionflame.imperiumserver.controller.form.DespesaForm;

@RestController
@RequestMapping("/despesas")
public class DespesaController {

	@PostMapping
	public ResponseEntity<?> adicionar(@RequestBody DespesaForm form) {
		return null;
	}

}
