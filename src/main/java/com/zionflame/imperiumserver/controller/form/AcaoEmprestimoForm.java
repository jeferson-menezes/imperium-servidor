package com.zionflame.imperiumserver.controller.form;

import com.zionflame.imperiumserver.model.AcaoEmprestimo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AcaoEmprestimoForm extends TransacaoForm{

	private Long emprestimoId;
	private Long contaId;
	
	public AcaoEmprestimo converter() {
		return new AcaoEmprestimo(valor, descricao, data, hora);
	}
}
