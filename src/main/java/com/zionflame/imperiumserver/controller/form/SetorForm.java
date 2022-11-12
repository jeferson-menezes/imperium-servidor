package com.zionflame.imperiumserver.controller.form;

import com.zionflame.imperiumserver.model.Setor;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SetorForm {

	private String nome;

	public Setor converter() {
		return new Setor(nome);
	}

}
