package com.zionflame.imperiumserver.controller.form;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ContaFormAtualiza {

	private String nome;
	private String descricao;
	private boolean incluiSoma;
	private Long tipoContaId;
	private boolean ativo;
	private BigDecimal saldo;
}
