package com.zionflame.imperiumserver.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zionflame.imperiumserver.helper.DateHelper;
import com.zionflame.imperiumserver.model.Cartao;
import com.zionflame.imperiumserver.model.CompraCartao;
import com.zionflame.imperiumserver.model.Fatura;
import com.zionflame.imperiumserver.model.ItemFatura;
import com.zionflame.imperiumserver.model.builder.FaturaBuilder;
import com.zionflame.imperiumserver.model.builder.ItemFaturaBuilder;
import com.zionflame.imperiumserver.model.enums.StatusFatura;
import com.zionflame.imperiumserver.repository.FaturaRepository;
import com.zionflame.imperiumserver.repository.ItemFaturaRepository;

@Service
public class FaturaService {

	@Autowired
	private FaturaRepository faturaRepository;

	@Autowired
	private ItemFaturaRepository itemFaturaRepository;

	public void registraCompra(CompraCartao compra) {
		for (int i = 0; i < compra.getParcelas(); i++) {
			
			Fatura fatura = pegaFatura(compra.getCartao(), compra.getData().plusMonths(i));

			ItemFatura item = new ItemFaturaBuilder()
					.comDescricao(compra.getDescricao())
					.comData(compra.getData())
					.comValor(ajustaValor(compra.getValorParcela(), compra.getParcelas(), compra.getValor(), i))
					.comParcela(nomeParcela(compra.getParcelas(), i))
					.comFatura(fatura)
					.comCompra(compra)
					.build();

			itemFaturaRepository.save(item);
		}

	}

	private String nomeParcela(int parcelas, int index) {
		if (parcelas > 1) {
			return String.valueOf(index + 1) + "/" + String.valueOf(parcelas);
		}
		return "";
	}

	private BigDecimal ajustaValor(BigDecimal parcela, int parcelas, BigDecimal valor, int i) {
		if (i == 0) {
			BigDecimal totais = parcela.multiply(new BigDecimal(parcelas));
			if (totais.compareTo(valor) != 0) {
				BigDecimal diferenca = valor.subtract(totais);
				parcela = diferenca.add(parcela);
			}
		}
		return parcela;
	}

	public void verificaExistenciaFaturas(List<Cartao> cartoes) {
		cartoes.forEach(cartao -> {
			pegaFatura(cartao, LocalDate.now());
		});
	}

	private Fatura pegaFatura(Cartao cartao, LocalDate data) {
		LocalDate fechamento = montaDataFechamento(cartao, data);
		if (data.isAfter(fechamento)) {
			fechamento = fechamento.plusMonths(1);
		}
		
		LocalDate vencimento = somaVencimento(fechamento, cartao.getDiaVencimento());
		
		Fatura fatura = faturaRepository
				.findByAnoAndMesAndCartaoId(vencimento.getYear(), vencimento.getMonthValue(),
				cartao.getId());

		if (fatura != null) {
			return fatura;
		}
		
		return criaFatura(fechamento, cartao);
	}

	private Fatura criaFatura(LocalDate data, Cartao cartao) {
		
		LocalDate vencimento = somaVencimento(data, cartao.getDiaVencimento());

		Fatura fatura = new FaturaBuilder()
				.comAbertura(data.minusMonths(1).plusDays(1))
				.comFechamento(data)
				.comVencimento(vencimento)
				.comMes(vencimento.getMonthValue())
				.comAno(vencimento.getYear())
				.comStatus(StatusFatura.ABERTA)
				.comCartao(cartao)
				.build();

		return faturaRepository.save(fatura);
	}

	private LocalDate somaVencimento(LocalDate fechamento, int vencimento) {
		return DateHelper.atingeDia(fechamento, vencimento);
	}

	private LocalDate montaDataFechamento(Cartao cartao, LocalDate data) {
		return DateHelper.lavaData(data.getYear(), data.getMonthValue(), cartao.getDiaFechamento());
	}

}








