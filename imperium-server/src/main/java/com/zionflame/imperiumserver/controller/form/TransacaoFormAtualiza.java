package com.zionflame.imperiumserver.controller.form;

import java.time.LocalDate;
import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class TransacaoFormAtualiza {

	private String descricao;
	private LocalDate data;
	private LocalDateTime hora;
	private Long categoriaId;

}
