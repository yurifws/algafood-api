package com.algaworks.algafood.domain.service;

import java.util.Set;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.Singular;

public interface EnvioEmailService {
	
	public void enviar(Mensagem mensagem);
	
	@Builder
	@Getter
	public class Mensagem{
		@Singular
		private Set<String> destinatarios;
		
		@NonNull
		private String assunto;
		
		@NonNull
		private String corpo;
	}

}
