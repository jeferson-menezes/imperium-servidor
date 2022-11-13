package com.zionflame.imperiumserver.repository.specification;

import org.springframework.data.jpa.domain.Specification;

import com.zionflame.imperiumserver.model.Ativo;

public class AtivoSpecification {

	public static Specification<Ativo> codigoLike(String codigo) {
		if (codigo == null) {
			return null;
		}
		return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.like(criteriaBuilder.lower(root.get("codigo")),
				"%" + codigo.toLowerCase() + "%");
	}

	public static Specification<Ativo> nomeLike(String nome) {
		if (nome == null) {
			return null;
		}
		return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.like(criteriaBuilder.lower(root.get("nome")),
				"%" + nome.toLowerCase() + "%");
	}

}
