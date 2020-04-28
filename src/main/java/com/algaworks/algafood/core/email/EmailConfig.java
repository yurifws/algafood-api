package com.algaworks.algafood.core.email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.algaworks.algafood.domain.service.EnvioEmailService;
import com.algaworks.algafood.infraestructure.service.email.FakeEnvioEmailServiceImpl;
import com.algaworks.algafood.infraestructure.service.email.SmtpEnvioEmailServiceImpl;

@Configuration
public class EmailConfig {
	
	@Autowired
	private EmailProperties emailProperties;
	
	@Bean
	public EnvioEmailService envioEmailService() {
		switch (emailProperties.getImpl()) {
		case FAKE:
			return new FakeEnvioEmailServiceImpl();
		case SMTP:
			return new SmtpEnvioEmailServiceImpl();
		default:
			return null;
	}
	}

}
