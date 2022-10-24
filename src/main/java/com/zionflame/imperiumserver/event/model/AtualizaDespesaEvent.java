package com.zionflame.imperiumserver.event.model;

import com.zionflame.imperiumserver.model.Usuario;

import lombok.Getter;

@Getter
public class AtualizaDespesaEvent {
	
	private final Long despesaId;
	private final Usuario usuario;

	public AtualizaDespesaEvent(Long despesaId, Usuario usuario) {
		this.despesaId = despesaId;
		this.usuario = usuario;
	}

}
