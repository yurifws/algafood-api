package com.algaworks.algafood.infraestructure.service.email;

import org.springframework.beans.factory.annotation.Autowired;

import com.algaworks.algafood.core.email.EmailProperties;


public class SandBoxEnvioEmailServiceImpl extends SmtpEnvioEmailServiceImpl {

	@Autowired
	private EmailProperties emailProperties;

	@Override
	public void enviar(Mensagem mensagem) {
		enviarMensagemPorDestinatario(mensagem, emailProperties.getSandbox().getDestinatario());
	}

}
