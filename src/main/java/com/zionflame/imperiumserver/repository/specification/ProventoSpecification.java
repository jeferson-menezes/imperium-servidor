package com.zionflame.imperiumserver.repository.specification;

import javax.persistence.criteria.Join;

import org.springframework.data.jpa.domain.Specification;

import com.zionflame.imperiumserver.helper.DateHelper;
import com.zionflame.imperiumserver.model.Ativo;
import com.zionflame.imperiumserver.model.Provento;
import com.zionflame.imperiumserver.model.Usuario;
import com.zionflame.imperiumserver.model.enums.Evento;

public class ProventoSpecification {

	public static Specification<Provento> usuarioEqual(Usuario usuario) {
		return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.equal(root.get("usuario"), usuario);
	}

	public static Specification<Provento> ativoIdEqual(Long ativoId) {
		if (ativoId == null) {
			return null;
		}
		return (root, criteriaQuery, criteriaBuilder) -> {
			Join<Provento, Ativo> ativoJoin = root.join("ativo");
			return criteriaBuilder.equal(ativoJoin.get("id"), ativoId);
		};
	}

	public static Specification<Provento> eventoEqual(Evento evento) {
		if (evento == null) {
			return null;
		}

		return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.equal(root.get("evento"), evento);
	}

	public static Specification<Provento> pagoEmMensalEqual(String pagoEm) {
		if (pagoEm == null) {
			return null;
		}

		DateHelper dateHelper = new DateHelper();

		return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.between(root.get("pagoEm"), dateHelper.formatOfMonthStart(pagoEm), dateHelper.formatOfMonthEnd(pagoEm));
	}

}
