package com.zionflame.imperiumserver.controller.form;

import java.time.LocalDate;
import java.time.LocalTime;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class TransacaoFormAtualiza {

	private String descricao;
	private LocalDate data;
	private LocalTime hora;
	private Long categoriaId;

}
