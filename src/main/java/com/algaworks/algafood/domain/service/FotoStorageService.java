package com.algaworks.algafood.domain.service;

import java.io.InputStream;
import java.util.UUID;

import lombok.Builder;
import lombok.Getter;

public interface FotoStorageService {
	
	void armazenar(NovaFoto novaFoto);
	
	default String gerarNomeArquivo(String nomeOriginal) {
		return String.format("%s_%s", UUID.randomUUID().toString(), nomeOriginal);
	}
	
	@Builder
	@Getter
	public class NovaFoto{
		private String nomeArquivo;
		private InputStream inputStream;
		
	}
	

}
