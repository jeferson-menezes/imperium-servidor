package com.zionflame.imperiumserver.repository.specification;

import java.util.Arrays;
import java.util.List;

import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;

import org.springframework.data.jpa.domain.Specification;

import com.zionflame.imperiumserver.model.Conta;
import com.zionflame.imperiumserver.model.Historia;
import com.zionflame.imperiumserver.model.Receita;
import com.zionflame.imperiumserver.model.Usuario;
import com.zionflame.imperiumserver.model.enums.Natureza;

public class HistoriaSpecification {

	public static Specification<Historia> usuarioEqual(Usuario usuario) {
		return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.equal(root.get("usuario"), usuario);
	}

	public static Specification<Historia> naturezaEqual(Natureza natureza) {
		if (natureza == null) {
			return null;
		}
		return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.equal(root.get("natureza"), natureza);
	}

	public static Specification<Historia> contaIdEqual(Long contaId) {

		if (contaId == null) {
			return null;
		}

		return (root, criteriaQuery, criteriaBuilder) -> {
			Join<Receita, Conta> contaJoin = root.join("conta", JoinType.LEFT);
			Join<Receita, Conta> contaDestinoJoin = root.join("contaDestino", JoinType.LEFT);

			criteriaQuery.distinct(true);

			List<Predicate> predicates = Arrays.asList(criteriaBuilder.equal(contaJoin.get("id"), contaId),
					criteriaBuilder.equal(contaDestinoJoin.get("id"), contaId));

			return criteriaBuilder.or(predicates.toArray(new Predicate[0]));
		};
	}

}
