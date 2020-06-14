package com.zionflame.imperiumserver.model;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Visual implements Serializable {

	private static final long serialVersionUID = -6233362146186313720L;
	
	private String icone;
	private String cor;
}
