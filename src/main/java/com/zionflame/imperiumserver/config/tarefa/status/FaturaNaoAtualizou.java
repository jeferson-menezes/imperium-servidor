package com.zionflame.imperiumserver.config.tarefa.status;

import com.zionflame.imperiumserver.model.Fatura;
import com.zionflame.imperiumserver.model.enums.StatusFatura;

public class FaturaNaoAtualizou extends FaturaAtualizou {

	public FaturaNaoAtualizou() {
		super(null);
	}

	@Override
	public StatusFatura verifica(Fatura fatura) {
		return fatura.getStatus();
	}

}
