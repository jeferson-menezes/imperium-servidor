package com.zionflame.imperiumserver.event;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.zionflame.imperiumserver.event.model.NovaNegociacaoEvent;
import com.zionflame.imperiumserver.service.PosicaoService;

@Component
public class NegociacaoListner {

	@Autowired
	private PosicaoService posicaoService;
	
	@Async
	@EventListener
	public void novaNegocicao(NovaNegociacaoEvent event) {

		posicaoService.adicionarNegociacao();
	}
}
