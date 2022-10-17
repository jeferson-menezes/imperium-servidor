package com.zionflame.imperiumserver.repository.specification;

import javax.persistence.criteria.Join;

import org.springframework.data.jpa.domain.Specification;

import com.zionflame.imperiumserver.model.Conta;
import com.zionflame.imperiumserver.model.Receita;
import com.zionflame.imperiumserver.model.Usuario;

public class ReceitaSpecification {
	
	public static Specification<Receita> usuarioEqual(Usuario usuario) {
		if (usuario == null) {
			return null;
		}
		return (root, criteriaQuery, criteriaBuilder) -> {
			Join<Receita, Conta> contaJoin = root.join("conta");
			return criteriaBuilder.equal(contaJoin.get("usuario"), usuario);

		};

	}

}
