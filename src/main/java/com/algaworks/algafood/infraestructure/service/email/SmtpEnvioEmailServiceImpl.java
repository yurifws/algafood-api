package com.algaworks.algafood.infraestructure.service.email;

import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import com.algaworks.algafood.core.email.EmailProperties;
import com.algaworks.algafood.domain.service.EnvioEmailService;

public class SmtpEnvioEmailServiceImpl implements EnvioEmailService {

	@Autowired
	private JavaMailSender mailSender;

	@Autowired
	private EmailProperties emailProperties;
	
	@Autowired
	private ProcessadorEmailTemplate processadorEmailTemplate;

	@Override
	public void enviar(Mensagem mensagem) {
		enviarMensagemPorDestinatario(mensagem, emailProperties.getRemetente());
	}

	protected void enviarMensagemPorDestinatario(Mensagem mensagem, String destinatario) {
		try {
			String corpo = processadorEmailTemplate.processarTemplate(mensagem);
			MimeMessage mimeMessage = mailSender.createMimeMessage();

			MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "UTF-8");
			helper.setFrom(destinatario);
			helper.setTo(mensagem.getDestinatarios().toArray(new String[0]));
			helper.setSubject(mensagem.getAssunto());
			helper.setText(corpo, true);

			mailSender.send(mimeMessage);
		} catch (Exception ex) {
			new EmailException("Não foi possível enviar e-mail.", ex);
		}
	}

	

}
