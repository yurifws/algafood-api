package com.algaworks.algafood.domain.service;

import java.io.InputStream;
import java.util.UUID;

import lombok.Builder;
import lombok.Getter;

public interface FotoStorageService {
	
	public void armazenar(NovaFoto novaFoto);
	public void remover(String nomeArquivo);
	
	public default void substituir(String nomeArquivoAntigo, NovaFoto novaFoto) {
		this.armazenar(novaFoto);
		if(nomeArquivoAntigo != null) {
			this.remover(nomeArquivoAntigo);
		}
	}
	
	public default String gerarNomeArquivo(String nomeOriginal) {
		return String.format("%s_%s", UUID.randomUUID().toString(), nomeOriginal);
	}
	
	@Builder
	@Getter
	public class NovaFoto{
		private String nomeArquivo;
		private InputStream inputStream;
		
	}
	

}
