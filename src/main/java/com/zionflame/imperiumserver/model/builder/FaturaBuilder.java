package com.zionflame.imperiumserver.model.builder;

import java.time.LocalDate;

import com.zionflame.imperiumserver.model.Cartao;
import com.zionflame.imperiumserver.model.Fatura;
import com.zionflame.imperiumserver.model.enums.StatusFatura;

public class FaturaBuilder {

	private LocalDate abertura;
	private LocalDate fechamento;
	private LocalDate vencimento;
	private int ano;
	private int mes;
	private StatusFatura status;
	private Cartao cartao;

	public FaturaBuilder comAbertura(LocalDate abertura) {
		this.abertura = abertura;
		return this;
	}

	public FaturaBuilder comFechamento(LocalDate fechamento) {
		this.fechamento = fechamento;
		return this;
	}

	public FaturaBuilder comVencimento(LocalDate vencimento) {
		this.vencimento = vencimento;
		return this;
	}

	public FaturaBuilder comAno(int ano) {
		this.ano = ano;
		return this;
	}

	public FaturaBuilder comMes(int mes) {
		this.mes = mes;
		return this;
	}

	public FaturaBuilder comStatus(StatusFatura status) {
		this.status = status;
		return this;
	}

	public FaturaBuilder comCartao(Cartao cartao) {
		this.cartao = cartao;
		return this;
	}

	public Fatura build() {
		return new Fatura(abertura, fechamento, vencimento, ano, mes, status, cartao);
	}
}
