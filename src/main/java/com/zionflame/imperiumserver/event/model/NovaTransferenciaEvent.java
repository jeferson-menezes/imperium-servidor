package com.zionflame.imperiumserver.event.model;

import com.zionflame.imperiumserver.model.Usuario;

import lombok.Getter;
@Getter
public class NovaTransferenciaEvent {

	private final Long transferenciaId;
	private final Usuario usuario;

	public NovaTransferenciaEvent(Long transferenciaId, Usuario usuario) {
		this.transferenciaId = transferenciaId;
		this.usuario = usuario;
	}
}
