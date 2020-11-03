package com.zionflame.imperiumserver.model;

import java.io.Serializable;

import javax.persistence.MappedSuperclass;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@MappedSuperclass
public class Visual implements Serializable {

	private static final long serialVersionUID = -6233362146186313720L;

	protected String icone;
	protected String cor;

	public Visual() {

	}

	public Visual(String cor, String icone) {
		this.cor = cor;
		this.icone = icone;
	}
}
