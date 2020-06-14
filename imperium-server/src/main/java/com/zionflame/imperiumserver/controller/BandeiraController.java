package com.zionflame.imperiumserver.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zionflame.imperiumserver.model.Bandeira;
import com.zionflame.imperiumserver.repository.BandeiraRepository;

@RestController
@RequestMapping("/bandeiras")
public class BandeiraController {

	@Autowired
	private BandeiraRepository bandeiraRepository;

	public List<Bandeira> listarTodas() {
		return bandeiraRepository.findAll();
	}
}
