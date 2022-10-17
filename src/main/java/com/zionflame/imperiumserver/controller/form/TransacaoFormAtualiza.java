package com.zionflame.imperiumserver.controller.form;

import java.math.BigDecimal;
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
	private BigDecimal valor;
	private Long contaId;

}
