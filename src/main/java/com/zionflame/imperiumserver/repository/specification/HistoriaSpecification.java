package com.zionflame.imperiumserver.repository.specification;

import org.springframework.data.jpa.domain.Specification;

import com.zionflame.imperiumserver.model.Historia;
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

}
