package com.zionflame.imperiumserver.controller.dto;

import org.springframework.data.domain.Page;

import com.zionflame.imperiumserver.model.TipoConta;
import com.zionflame.imperiumserver.model.Transferencia;

import lombok.Getter;

@Getter
public class TransferenciaDto extends TransacaoDto {

	private static final long serialVersionUID = -3828078832169110437L;
	private String contaDestinoNome;
	private String contaOrigemNome;
	private TipoConta contaDestinoTipo;
	private TipoConta contaOrigemTipo;

	public TransferenciaDto(Transferencia transferencia) {
		super(transferencia);
		contaOrigemNome = transferencia.getContaOrigem().getNome();
		contaOrigemTipo = transferencia.getContaOrigem().getTipo();
		contaDestinoNome = transferencia.getContaDestino().getNome();
		contaDestinoTipo = transferencia.getContaDestino().getTipo();
	}

	public static Page<TransferenciaDto> converter(Page<Transferencia> transferencias) {
		return transferencias.map(TransferenciaDto::new);
	}

}
