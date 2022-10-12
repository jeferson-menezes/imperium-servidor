package com.zionflame.imperiumserver.config.exeption;

import java.io.Serializable;
import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Setter
@Getter
@SuperBuilder
public class ExceptionDetails implements Serializable {
	
	private static final long serialVersionUID = -6954852819818016625L;
	
	private String title;
	private Integer status;
	private String message;
	private String developerMessage;
	public LocalDateTime timestamp;

}
