package com.zionflame.imperiumserver.controller.form;

import javax.validation.constraints.NotNull;

import com.zionflame.imperiumserver.model.Despesa;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DespesaForm extends TransacaoForm {

	@NotNull
	private Long categoriaId;
	@NotNull
	private Long contaId;

	public Despesa converter() {
		return new Despesa(valor, descricao, data, hora, Boolean.TRUE);
	}
}
