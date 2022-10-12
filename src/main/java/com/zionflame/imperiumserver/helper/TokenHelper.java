package com.zionflame.imperiumserver.helper;

import javax.servlet.http.HttpServletRequest;

public interface TokenHelper {

	default String recuperarToken(HttpServletRequest request) {
		String token = request.getHeader("Authorization");
		if (token == null || token.isEmpty() || !token.startsWith("Bearer ")) {
			return null;
		}
		return token.substring(7, token.length());
	}
}
