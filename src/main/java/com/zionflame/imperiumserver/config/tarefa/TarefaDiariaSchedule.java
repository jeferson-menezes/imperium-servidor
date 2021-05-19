package com.zionflame.imperiumserver.config.tarefa;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import com.zionflame.imperiumserver.repository.CartaoRepository;
import com.zionflame.imperiumserver.repository.FaturaRepository;
import com.zionflame.imperiumserver.service.FaturaService;

@Configuration
@EnableScheduling
public class TarefaDiariaSchedule implements Tarefa {

	
	@Autowired
	private CartaoRepository cartaoRepository;
	
	@Autowired
	private FaturaService faturaService;
	
	@Autowired
	private FaturaRepository faturaRepository;
	
//	s | m | h | d | m | a
//	@Scheduled(cron = "0 21 21 * * *")
	@Override
	public void executa() {

		System.out.println("Executando schedule " + LocalDateTime.now());
		
		CriacaoFaturaHandler criacaoFaturaHandler = new CriacaoFaturaHandler(cartaoRepository ,faturaService);		
		criacaoFaturaHandler.executa();

		AtualizaStatusFaturaHandler atualizaStatusFaturaHandler = new AtualizaStatusFaturaHandler(cartaoRepository, faturaRepository);
		atualizaStatusFaturaHandler.executa();
	}

}
