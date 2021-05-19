package com.zionflame.imperiumserver.config.tarefa.status;

import java.time.LocalDate;

import com.zionflame.imperiumserver.model.Fatura;
import com.zionflame.imperiumserver.model.enums.StatusFatura;

public class FaturaJaEVencida extends FaturaAtualizou {

	public FaturaJaEVencida(FaturaAtualizou proximo) {
		super(proximo);
	}

	@Override
	public StatusFatura verifica(Fatura fatura) {

		if (fatura.getStatus() == StatusFatura.ABERTA || 
				fatura.getStatus() == StatusFatura.FECHADA) {

			if (fatura.getVencimento().isBefore(LocalDate.now())) {

				return StatusFatura.VENCIDA;
			}
		}

		return proximo.verifica(fatura);
	}

}
