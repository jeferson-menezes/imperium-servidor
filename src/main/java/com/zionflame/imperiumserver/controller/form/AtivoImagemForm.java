package com.zionflame.imperiumserver.controller.form;

import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AtivoImagemForm {
	@NotNull
	private Long imagemId;
}
