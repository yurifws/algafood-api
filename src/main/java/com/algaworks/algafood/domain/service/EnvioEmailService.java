package com.algaworks.algafood.domain.service;

import java.util.Set;

import lombok.Builder;
import lombok.Getter;

public interface EnvioEmailService {
	
	public void enviar(Mensagem mensagem);
	
	@Builder
	@Getter
	public class Mensagem{
		private Set<String> destinatarios;
		private String assunto;
		private String corpo;
	}

}
