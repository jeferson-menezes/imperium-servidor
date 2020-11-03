package com.zionflame.imperiumserver.config.security;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.zionflame.imperiumserver.config.security.dto.LogadoDto;
import com.zionflame.imperiumserver.model.Usuario;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class TokenService {

	@Value("${forum.jwt.expiration}")
	private String expiration;

	@Value("${forum.jwt.secret}")
	private String secret;

	public LogadoDto gerarToken(Authentication authenticate) {
		Usuario usuario = (Usuario) authenticate.getPrincipal();
		Date hoje = new Date();
		Date dataExpiracao = new Date(hoje.getTime() + Long.valueOf(expiration));

		String token = Jwts.builder().setIssuer("API Zion Flame Imperium").setSubject(usuario.getId().toString())
				.setIssuedAt(hoje).setExpiration(dataExpiracao).signWith(SignatureAlgorithm.HS256, secret).compact();

		return new LogadoDto(token, usuario);
	}

	public boolean isTokenValido(String token) {
		try {
			Jwts.parser().setSigningKey(this.secret).parseClaimsJws(token);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public Long getIdUsuario(String token) {
		Claims claims = Jwts.parser().setSigningKey(this.secret).parseClaimsJws(token).getBody();
		return Long.parseLong(claims.getSubject());
	}

}
