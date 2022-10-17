package com.zionflame.imperiumserver.repository.specification;

import java.math.BigDecimal;

import javax.persistence.criteria.Join;

import org.springframework.data.jpa.domain.Specification;

import com.zionflame.imperiumserver.helper.DateHelper;
import com.zionflame.imperiumserver.model.Conta;
import com.zionflame.imperiumserver.model.Receita;
import com.zionflame.imperiumserver.model.Usuario;

public class ReceitaSpecification {
	public static Specification<Receita> usuarioEqual(Usuario usuario) {
		return (root, criteriaQuery, criteriaBuilder) -> {
			Join<Receita, Conta> contaJoin = root.join("conta");
			return criteriaBuilder.equal(contaJoin.get("usuario"), usuario);

		};

	}

	public static Specification<Receita> dataEqual(String data) {
		if (data == null) {
			return null;
		}
		DateHelper dateHelper = new DateHelper();
		return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.between(root.get("data"),
				dateHelper.sToDatetimeStart(data), dateHelper.sToDatetimeEnd(data));
	}

	public static Specification<Receita> dataMensalEqual(String data) {
		if (data == null) {
			return null;
		}
		DateHelper dateHelper = new DateHelper();
		return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.between(root.get("data"),
				dateHelper.formatOfMonthStart(data), dateHelper.formatOfMonthEnd(data));
	}

	public static Specification<Receita> descricaoLike(String descricao) {
		if (descricao == null) {
			return null;
		}
		return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder
				.like(criteriaBuilder.lower(root.get("descricao")), "%" + descricao.toLowerCase() + "%");
	}

	public static Specification<Receita> valorEqual(BigDecimal valor) {
		if (valor == null) {
			return null;
		}
		return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.equal(root.get("valor"), valor);
	}

}
