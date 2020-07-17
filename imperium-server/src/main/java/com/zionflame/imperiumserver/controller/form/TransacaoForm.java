package com.zionflame.imperiumserver.controller.form;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TransacaoForm {

	@NotNull
	protected BigDecimal valor;
	@NotNull
	protected String descricao;
	@NotNull
	protected LocalDate data;
	@NotNull
	protected LocalTime hora;
	
}
