package com.zionflame.imperiumserver.config.tarefa.status;

import com.zionflame.imperiumserver.model.Fatura;
import com.zionflame.imperiumserver.model.enums.StatusFatura;

public class FaturaEPagaOUVencida extends FaturaAtualizou {

	public FaturaEPagaOUVencida(FaturaAtualizou proximo) {
		super(proximo);
	}

	@Override
	public StatusFatura verifica(Fatura fatura) {
		
		if (fatura.getStatus() == StatusFatura.PAGA) {
			return StatusFatura.PAGA;
		}

		if (fatura.getStatus() == StatusFatura.VENCIDA) {
			return StatusFatura.VENCIDA;
		}

		return proximo.verifica(fatura);
	}

}
