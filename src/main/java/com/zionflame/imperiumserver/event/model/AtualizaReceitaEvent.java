package com.zionflame.imperiumserver.event.model;

import com.zionflame.imperiumserver.model.Usuario;

import lombok.Getter;

@Getter
public class AtualizaReceitaEvent {
	
	private final Long receitaId;
	private final Usuario usuario;

	public AtualizaReceitaEvent(Long receitaId, Usuario usuario) {
		this.receitaId = receitaId;
		this.usuario = usuario;
	}

}
