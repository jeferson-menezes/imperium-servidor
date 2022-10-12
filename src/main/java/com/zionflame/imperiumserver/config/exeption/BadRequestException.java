package com.zionflame.imperiumserver.config.exeption;

public class BadRequestException extends RuntimeException {

	private static final long serialVersionUID = 8245434617257121936L;

	public BadRequestException(String message) {
		super(message);
	}
}
