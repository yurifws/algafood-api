package com.algaworks.algafood.di.listener;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.di.service.event.ClienteAtivadoEvent;

@Component
public class EmissaoNotaFiscalService {	
	
	@EventListener
	public void clienteAtivadoListener(ClienteAtivadoEvent event) {
		System.out.println("Emitido nota fiscal para cliente "+ event.getCliente().getNome());
	}

}
