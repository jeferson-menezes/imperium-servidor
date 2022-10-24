package com.zionflame.imperiumserver.event.model;

import com.zionflame.imperiumserver.model.Usuario;

import lombok.Getter;

@Getter
public class NovaReceitaEvent {

	private final Long receitaId;
	private final Usuario usuario;

	public NovaReceitaEvent(Long receitaId, Usuario usuario) {
		this.receitaId = receitaId;
		this.usuario = usuario;
	}

}
