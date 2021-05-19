package com.zionflame.imperiumserver.config.tarefa.status;

import java.time.LocalDate;

import com.zionflame.imperiumserver.model.Fatura;
import com.zionflame.imperiumserver.model.enums.StatusFatura;

public class FaturaJaEFutura extends FaturaAtualizou {

	public FaturaJaEFutura(FaturaAtualizou proximo) {
		super(proximo);
	}

	@Override
	public StatusFatura verifica(Fatura fatura) {

		if (fatura.getStatus() == StatusFatura.ABERTA 
				&& fatura.getAbertura().isAfter(LocalDate.now())) {
			return StatusFatura.FUTURA;
		}

		return proximo.verifica(fatura);
	}

}
