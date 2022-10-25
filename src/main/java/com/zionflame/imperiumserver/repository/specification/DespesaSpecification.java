package com.zionflame.imperiumserver.repository.specification;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;

import org.springframework.data.jpa.domain.Specification;

import com.zionflame.imperiumserver.helper.DateHelper;
import com.zionflame.imperiumserver.model.Categoria;
import com.zionflame.imperiumserver.model.Conta;
import com.zionflame.imperiumserver.model.Despesa;
import com.zionflame.imperiumserver.model.Usuario;

public class DespesaSpecification {

	public static Specification<Despesa> contaIdAndcontaUsuarioEqual(Long contaId, Usuario usuario) {
		if (usuario == null && contaId == null) {
			return null;
		}

		return (root, criteriaQuery, criteriaBuilder) -> {

			Join<Despesa, Conta> contaJoin = root.join("conta");

			List<Predicate> predicates = new ArrayList<Predicate>();

			if (usuario != null) {
				predicates.add(criteriaBuilder.equal(contaJoin.get("usuario"), usuario));
			}

			if (contaId != null) {
				predicates.add(criteriaBuilder.equal(contaJoin.get("id"), contaId));
			}

			return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
		};
	}

	public static Specification<Despesa> categoriaIdEqual(Long categoriaId) {
		if (categoriaId == null) {
			return null;
		}
		return (root, criteriaQuery, criteriaBuilder) -> {
			Join<Despesa, Categoria> categoriaJoin = root.join("categoria");
			return criteriaBuilder.equal(categoriaJoin.get("id"), categoriaId);
		};
	}

	public static Specification<Despesa> dataEqual(String data) {
		if (data == null) {
			return null;
		}
		DateHelper dateHelper = new DateHelper();
		return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.between(root.get("data"),
				dateHelper.sToDatetimeStart(data), dateHelper.sToDatetimeEnd(data));
	}

	public static Specification<Despesa> dataMensalEqual(String data) {
		if (data == null) {
			return null;
		}
		DateHelper dateHelper = new DateHelper();
		return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.between(root.get("data"),
				dateHelper.formatOfMonthStart(data), dateHelper.formatOfMonthEnd(data));
	}

	public static Specification<Despesa> descricaoLike(String descricao) {
		if (descricao == null) {
			return null;
		}
		return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder
				.like(criteriaBuilder.lower(root.get("descricao")), "%" + descricao.toLowerCase() + "%");
	}

	public static Specification<Despesa> valorEqual(BigDecimal valor) {
		if (valor == null) {
			return null;
		}
		return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.equal(root.get("valor"), valor);
	}

}
