package com.algaworks.algafood.infraestructure.service.storage;

import java.io.InputStream;

import org.springframework.stereotype.Service;

import com.algaworks.algafood.domain.service.FotoStorageService;
import com.amazonaws.services.s3.AmazonS3;

@Service
public class S3FotoStorageService implements FotoStorageService {
	
	private AmazonS3 amazonS3;

	@Override
	public InputStream recuperar(String nomeArquivo) {
		return null;
	}

	@Override
	public void armazenar(NovaFoto novaFoto) {
		
	}

	@Override
	public void remover(String nomeArquivo) {
		
	}

}
