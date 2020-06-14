package com.zionflame.imperiumserver.controller.form;

import com.zionflame.imperiumserver.controller.dto.TransacaoDto;
import com.zionflame.imperiumserver.model.AcaoEmprestimo;
import com.zionflame.imperiumserver.model.enums.NaturezaEmprestimo;

import lombok.Getter;

@Getter
public class AcaoEmprestimoDto extends TransacaoDto{

	private static final long serialVersionUID = -5725598923099020513L;

	private String contaNome;
	private String emprestimoDescricao;
	private NaturezaEmprestimo emprestimoNatureza;

	public AcaoEmprestimoDto(AcaoEmprestimo acao) {
		super(acao);
		contaNome = acao.getConta().getNome();
		emprestimoDescricao = acao.getEmprestimo().getDescricao();
		emprestimoNatureza = acao.getEmprestimo().getNatureza();
	}

}
