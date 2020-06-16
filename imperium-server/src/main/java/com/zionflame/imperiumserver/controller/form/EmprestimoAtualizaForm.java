package com.zionflame.imperiumserver.controller.form;

import java.time.LocalDate;
import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmprestimoAtualizaForm {

	private String pessoa;
	protected String descricao;
	protected LocalDate data;
	protected LocalDateTime hora;

}