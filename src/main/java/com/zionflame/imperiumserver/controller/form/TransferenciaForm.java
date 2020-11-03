package com.zionflame.imperiumserver.controller.form;

import com.zionflame.imperiumserver.model.Transferencia;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TransferenciaForm extends TransacaoForm {

	private Long contaOrigemId;
	private Long contaDestinoId;

	public Transferencia converter() {
		return new Transferencia(valor, descricao, data, hora);
	}
}
