package com.zionflame.imperiumserver.config.security.interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.zionflame.imperiumserver.config.security.TokenService;
import com.zionflame.imperiumserver.repository.UsuarioRepository;

@Configuration
public class InterceptorsConfig implements WebMvcConfigurer {

	@Autowired
	private TokenService tokenService;
	@Autowired
	private UsuarioRepository usuarioRepository;

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(new UsuarioLogadoInterceptor(tokenService, usuarioRepository)).addPathPatterns("/**")
				.excludePathPatterns("/auth/**");
	}
}
