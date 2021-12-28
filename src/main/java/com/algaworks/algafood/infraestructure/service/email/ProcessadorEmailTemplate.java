package com.algaworks.algafood.infraestructure.service.email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import com.algaworks.algafood.domain.service.EnvioEmailService.Mensagem;

import freemarker.template.Configuration;
import freemarker.template.Template;

@Component
public class ProcessadorEmailTemplate {

	@Autowired
	private Configuration configurationFreeMarker;
	
	protected String processarTemplate(Mensagem mensagem) {
		try {
			Template template = configurationFreeMarker.getTemplate(mensagem.getCorpo());
			return FreeMarkerTemplateUtils.processTemplateIntoString(template, mensagem.getVariaveis());
		} catch (Exception ex) {
			new EmailException("Não foi possível montar o template do e-mail.", ex);
		}
		return null;
	}

}
