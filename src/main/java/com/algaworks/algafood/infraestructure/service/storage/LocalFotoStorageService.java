package com.algaworks.algafood.infraestructure.service.storage;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.FileCopyUtils;

import com.algaworks.algafood.core.storage.StorageProperties;
import com.algaworks.algafood.domain.service.FotoStorageService;

//@Service
public class LocalFotoStorageService implements FotoStorageService {

	@Autowired
	private StorageProperties storageProperties;

	@Override
	public void armazenar(NovaFoto novaFoto) {
		try {
			Path path = getArquivoPath(novaFoto.getNomeArquivo());
			FileCopyUtils.copy(novaFoto.getInputStream(), Files.newOutputStream(path));
		} catch (Exception ex) {
			new StorageException("Não foi possível armazenar arquivo.", ex);
		}

	}

	@Override
	public void remover(String nomeArquivo) {
		try {
			Path path = getArquivoPath(nomeArquivo);
			Files.deleteIfExists(path);
		} catch (Exception ex) {
			new StorageException("Não foi possível remover arquivo.", ex);
		}
	}

	private Path getArquivoPath(String nomeArquivo) {
		return storageProperties.getLocal().getDiretorioFotos()
				.resolve(Path.of(nomeArquivo));
	}

	@Override
	public InputStream recuperar(String nomeArquivo) {
		try {
			Path path = getArquivoPath(nomeArquivo);
			return Files.newInputStream(path);
		} catch (Exception ex) {
			new StorageException("Não foi possível recuperar arquivo.", ex);
		}
		return null;
	}

}
