package com.zionflame.imperiumserver.event;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.zionflame.imperiumserver.event.model.AtualizaDespesaEvent;
import com.zionflame.imperiumserver.event.model.AtualizaReceitaEvent;
import com.zionflame.imperiumserver.event.model.NovaDespesaEvent;
import com.zionflame.imperiumserver.event.model.NovaReceitaEvent;
import com.zionflame.imperiumserver.event.model.NovaTransferenciaEvent;
import com.zionflame.imperiumserver.model.Usuario;
import com.zionflame.imperiumserver.service.HistoriaService;

@Component
public class HistoriaListner {

	@Autowired
	private HistoriaService historiaService;

	@Async
	@EventListener
	public void novaTrasferencia(NovaTransferenciaEvent event) {
		Long transferenciaId = event.getTransferenciaId();
		Usuario usuario = event.getUsuario();
		historiaService.adicionaTransferencia(transferenciaId, usuario);
	}

	@Async
	@EventListener
	public void novaDespesa(NovaDespesaEvent event) {
		Long despesaId = event.getDespesaId();
		Usuario usuario = event.getUsuario();
		historiaService.adicionaDespesa(despesaId, usuario);
	}
	
	@Async
	@EventListener
	public void atualizaDespesa(AtualizaDespesaEvent event) {
		Long despesaId = event.getDespesaId();
		Usuario usuario = event.getUsuario();
		historiaService.atualizaDespesa(despesaId, usuario);
	}
	
	@Async
	@EventListener
	public void novaReceita(NovaReceitaEvent  event) {
		Long receitaId = event.getReceitaId();
		Usuario usuario = event.getUsuario();
		historiaService.adicionaReceita(receitaId, usuario);
	}
	
	@Async
	@EventListener
	public void atualizaReceita(AtualizaReceitaEvent  event) {
		Long receitaId = event.getReceitaId();
		Usuario usuario = event.getUsuario();
		historiaService.atualizaReceita(receitaId, usuario);
	}
}

