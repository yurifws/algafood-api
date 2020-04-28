package com.algaworks.algafood.infraestructure.service.email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.algaworks.algafood.core.email.EmailProperties;

@Service
public class SandBoxEnvioEmailServiceImpl extends SmtpEnvioEmailServiceImpl {

	@Autowired
	private EmailProperties emailProperties;

	@Override
	public void enviar(Mensagem mensagem) {
		enviarMensagemPorDestinatario(mensagem, emailProperties.getSandbox().getDestinatario());
	}

}
