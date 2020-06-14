package com.zionflame.imperiumserver.controller.form;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TransacaoForm {

	protected BigDecimal valor;
	protected String descricao;
	protected LocalDateTime data;
	protected LocalDateTime hora;
}
