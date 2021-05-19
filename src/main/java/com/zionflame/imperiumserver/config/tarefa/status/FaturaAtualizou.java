package com.zionflame.imperiumserver.config.tarefa.status;

import com.zionflame.imperiumserver.model.Fatura;
import com.zionflame.imperiumserver.model.enums.StatusFatura;

public abstract class FaturaAtualizou {

	protected FaturaAtualizou proximo;

	public FaturaAtualizou(FaturaAtualizou proximo) {
		this.proximo = proximo;
	}

	public abstract StatusFatura verifica(Fatura fatura);
}
