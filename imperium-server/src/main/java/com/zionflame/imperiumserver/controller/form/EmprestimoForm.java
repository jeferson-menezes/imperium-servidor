package com.zionflame.imperiumserver.controller.form;

import com.zionflame.imperiumserver.model.Emprestimo;
import com.zionflame.imperiumserver.model.enums.NaturezaEmprestimo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmprestimoForm extends TransacaoForm {

	private String pessoa;
	private NaturezaEmprestimo natureza;
	private Long contaId;
	private Long usuarioId;

	public Emprestimo converter() {
		return new Emprestimo(pessoa, natureza, valor, descricao, data, hora);
	}
}
