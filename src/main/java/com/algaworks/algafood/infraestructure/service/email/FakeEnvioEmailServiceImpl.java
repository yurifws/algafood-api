package com.algaworks.algafood.infraestructure.service.email;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FakeEnvioEmailServiceImpl extends SmtpEnvioEmailServiceImpl {

	@Override
	public void enviar(Mensagem mensagem) {
		String corpo = processarTemplate(mensagem);
		log.info("[FAKE E-MAIL] Para: {}\n{}", mensagem.getDestinatarios(), corpo);

	}

}
