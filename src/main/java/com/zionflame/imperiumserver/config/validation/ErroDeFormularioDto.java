package com.zionflame.imperiumserver.config.validation;

import lombok.Getter;

@Getter
public class ErroDeFormularioDto {

	private String erro;
	private String campo;

	public ErroDeFormularioDto(String campo, String erro) {
		this.campo = campo;
		this.erro = erro;
	}

}
