package com.algaworks.algafood.di.service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.di.modelo.Cliente;
import com.algaworks.algafood.di.notificacao.NivelUrgencia;
import com.algaworks.algafood.di.notificacao.Notificador;
import com.algaworks.algafood.di.notificacao.TipoDoNotificador;


//@Component
public class AtivacaoClienteService implements InitializingBean, DisposableBean{

	@TipoDoNotificador(NivelUrgencia.SEM_URGENCIA)
	@Autowired
	private Notificador notificador;
	
	//@PostConstruct
//	public void init() {
//		System.out.println("INIT "+notificador);
//	}
	

	public void ativar(Cliente cliente) {
		cliente.ativar();

		this.notificador.notificar(cliente, "Seu cadastro no sistema está ativo!");

	}

	@Override
	public void afterPropertiesSet() throws Exception {
		System.out.println("INIT "+notificador);
	}

	//@PreDestroy
	@Override
	public void destroy() throws Exception{
		System.out.println("DESTROY AtivacaoClienteService");
	}
	
	

}
