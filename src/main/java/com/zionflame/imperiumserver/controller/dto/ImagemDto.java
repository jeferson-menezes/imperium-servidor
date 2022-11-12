package com.zionflame.imperiumserver.controller.dto;

import com.zionflame.imperiumserver.model.Imagem;

import lombok.Getter;

@Getter
public class ImagemDto {

	private final Long id;
	private final String fileName;
	private final String mimeType;
	private final String name;
	private final Long size;

	public ImagemDto(Imagem imagem) {
		id = imagem.getId();
		fileName = imagem.getFileName();
		mimeType = imagem.getMimeType();
		name = imagem.getName();
		size = imagem.getSize();
	}

}
