package com.zionflame.imperiumserver.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zionflame.imperiumserver.model.Imagem;
import com.zionflame.imperiumserver.repository.ImagemRepository;

@Service
public class ImagemService {

	@Autowired
	private ImagemRepository imagemRepository;

	public void marcarParaExcluir(Imagem imagem) {
		imagem.setDeleted(Boolean.TRUE);
		imagemRepository.save(imagem);
	}
}
