package com.zionflame.imperiumserver.config.tarefa.status;

import java.time.LocalDate;

import com.zionflame.imperiumserver.model.Fatura;
import com.zionflame.imperiumserver.model.enums.StatusFatura;

public class FaturaJaEFechada extends FaturaAtualizou {

	public FaturaJaEFechada(FaturaAtualizou proximo) {
		super(proximo);
	}

	@Override
	public StatusFatura verifica(Fatura fatura) {
		
		LocalDate hoje = LocalDate.now();
		
		if (fatura.getStatus() == StatusFatura.ABERTA 
				&& hoje.isAfter(fatura.getFechamento().minusDays(1))
				&& hoje.isBefore(fatura.getVencimento().plusDays(1))) {

			return StatusFatura.FECHADA;
		}

		return proximo.verifica(fatura);
	}

}
