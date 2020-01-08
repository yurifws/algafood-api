package com.algaworks.algafood.di.notificacao;

import com.algaworks.algafood.di.modelo.Cliente;

//@Component
public class NotificadorEmail implements Notificador {
	
	private String smtp;
	private boolean caixaAlta;
	
	public NotificadorEmail(String smtp) {
		this.smtp = smtp;
		System.out.println("NotificadorEmail");
	}
	
	@Override
	public void notificar(Cliente cliente, String mensagem) {
		if(this.caixaAlta) {
			mensagem = mensagem.toUpperCase();
		}
		
		System.out.printf("Notificando %s através do e-mail %s: smtp: %s %s\n", cliente.getNome(), cliente.getEmail(), smtp, mensagem);
	}

	public boolean isCaixaAlta() {
		return caixaAlta;
	}

	public void setCaixaAlta(boolean caixaAlta) {
		this.caixaAlta = caixaAlta;
	}

	
}
