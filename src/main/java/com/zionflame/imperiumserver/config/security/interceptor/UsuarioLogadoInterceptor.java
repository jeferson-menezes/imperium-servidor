package com.zionflame.imperiumserver.config.security.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;

import com.zionflame.imperiumserver.config.exeption.BadRequestException;
import com.zionflame.imperiumserver.config.security.TokenService;
import com.zionflame.imperiumserver.helper.ConstantsHelper;
import com.zionflame.imperiumserver.helper.TokenHelper;
import com.zionflame.imperiumserver.model.Usuario;
import com.zionflame.imperiumserver.repository.UsuarioRepository;

public class UsuarioLogadoInterceptor implements HandlerInterceptor, TokenHelper, ConstantsHelper {

	private final TokenService tokenService;

	private final UsuarioRepository usuarioRepository;

	public UsuarioLogadoInterceptor(TokenService tokenService, UsuarioRepository usuarioRepository) {
		this.tokenService = tokenService;
		this.usuarioRepository = usuarioRepository;
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {

		String token = recuperarToken(request);

		Long usuarioId = tokenService.getIdUsuario(token);

		Usuario usuario = usuarioRepository.findById(usuarioId)
				.orElseThrow(() -> new BadRequestException("Usuário inválido"));

		request.setAttribute(USUARIO_ID_ATT_REQ, usuarioId);
		request.setAttribute(USUARIO_ATT_REQ, usuario);

		return true;

	}
}
