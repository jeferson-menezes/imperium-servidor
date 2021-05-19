package com.zionflame.imperiumserver.config.tarefa.status;

import java.time.LocalDate;

import com.zionflame.imperiumserver.model.Fatura;
import com.zionflame.imperiumserver.model.enums.StatusFatura;

public class FaturaJaEAberta extends FaturaAtualizou {

	public FaturaJaEAberta(FaturaAtualizou proximo) {
		super(proximo);
	}

	@Override
	public StatusFatura verifica(Fatura fatura) {
		LocalDate hoje = LocalDate.now();

		if (fatura.getStatus() == StatusFatura.FUTURA 
				&& hoje.isAfter(fatura.getAbertura().minusDays(1))
				&& hoje.isBefore(fatura.getFechamento().plusDays(1))) {
			
			return StatusFatura.PAGA;
		}

		return proximo.verifica(fatura);
	}

}
