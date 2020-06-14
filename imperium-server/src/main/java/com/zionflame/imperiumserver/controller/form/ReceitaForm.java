package com.zionflame.imperiumserver.controller.form;

import com.zionflame.imperiumserver.model.Receita;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReceitaForm extends TransacaoForm {

	private boolean concluida;
	private Long categoriaId;
	private Long contaId;

	public Receita converter() {
		return new Receita(valor, descricao, data, hora, concluida);
	}
}
