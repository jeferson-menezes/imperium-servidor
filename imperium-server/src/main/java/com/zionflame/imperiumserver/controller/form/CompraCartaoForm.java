package com.zionflame.imperiumserver.controller.form;

import java.math.BigDecimal;

import com.zionflame.imperiumserver.model.CompraCartao;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CompraCartaoForm extends TransacaoForm {

	private int parcelas;
	private BigDecimal valorParcela;
	private Long cartaoId;
	private Long categoriaId;

	public CompraCartao converter() {
		return new CompraCartao(parcelas, valorParcela, valor, descricao, data, hora);
	}

}
