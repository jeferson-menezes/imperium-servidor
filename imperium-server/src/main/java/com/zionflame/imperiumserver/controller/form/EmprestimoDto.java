package com.zionflame.imperiumserver.controller.form;

import com.zionflame.imperiumserver.controller.dto.TransacaoDto;
import com.zionflame.imperiumserver.model.Emprestimo;
import com.zionflame.imperiumserver.model.enums.NaturezaEmprestimo;

import lombok.Getter;

@Getter
public class EmprestimoDto extends TransacaoDto {

	private static final long serialVersionUID = -8110948752257015031L;

	private String pessoa;
	private NaturezaEmprestimo natureza;
	private String usuarioNome;
	private String contaNome;

	public EmprestimoDto(Emprestimo emprestimo) {
		super(emprestimo);
		contaNome = emprestimo.getConta().getNome();
		natureza = emprestimo.getNatureza();
		pessoa = emprestimo.getPessoa();
		usuarioNome = emprestimo.getUsuario().getNome();

	}

}
