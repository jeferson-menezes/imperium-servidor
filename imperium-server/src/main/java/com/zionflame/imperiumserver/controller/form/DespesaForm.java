package com.zionflame.imperiumserver.controller.form;

import com.zionflame.imperiumserver.model.Despesa;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DespesaForm extends TransacaoForm {

	private boolean concluida;
	private Long categoriaId;
	private Long contaId;

	public Despesa converter() {
		return new Despesa(valor, descricao, data, hora, concluida);
	}
}
