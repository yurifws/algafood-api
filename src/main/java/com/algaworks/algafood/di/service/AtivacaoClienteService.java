package com.algaworks.algafood.di.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.di.modelo.Cliente;
import com.algaworks.algafood.di.notificacao.Notificador;

@Component
public class AtivacaoClienteService {
	
	@Autowired(required = false)
	private List<Notificador> notificadores;

	
	public void ativar(Cliente cliente) {
		cliente.ativar();
		
		if(notificadores !=null) {
			for(Notificador notificador: notificadores) {
				
				notificador.notificar(cliente, "Seu cadastro no sistema está ativo!");
			}
		}else {
			System.out.println("Autowired Opcional");
		}
		
		
	}

	

}
