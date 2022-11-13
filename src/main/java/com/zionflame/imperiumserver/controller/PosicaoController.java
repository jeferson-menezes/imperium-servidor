package com.zionflame.imperiumserver.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zionflame.imperiumserver.helper.ConstantsHelper;
import com.zionflame.imperiumserver.repository.PosicaoRepository;

@RestController
@RequestMapping("/posicoes")
public class PosicaoController implements ConstantsHelper{
	
	@Autowired
	private PosicaoRepository posicaoRepository;

}
