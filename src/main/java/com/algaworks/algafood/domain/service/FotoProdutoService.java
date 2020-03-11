package com.algaworks.algafood.domain.service;

import java.io.InputStream;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.algaworks.algafood.domain.exception.FotoProdutoNaoEncontradoException;
import com.algaworks.algafood.domain.model.FotoProduto;
import com.algaworks.algafood.domain.repository.ProdutoRepository;
import com.algaworks.algafood.domain.service.FotoStorageService.NovaFoto;

@Service
public class FotoProdutoService {

	@Autowired
	private ProdutoRepository produtoRepository;

	@Autowired
	private FotoStorageService fotoStorageService;
	
	public FotoProduto buscar(Long restauranteId,Long produtoId) {
		return produtoRepository.findFotoProdutoById(restauranteId, produtoId).orElseThrow(() -> new FotoProdutoNaoEncontradoException(restauranteId, produtoId));
	}


	@Transactional
	public FotoProduto salvar(FotoProduto fotoProduto, InputStream inputStream) {
		Long restauranteId = fotoProduto.getRestauranteId();
		Long produtoId = fotoProduto.getProduto().getId();
		Optional<FotoProduto> fotoProdutoExistente = produtoRepository.findFotoProdutoById(restauranteId, produtoId);
		fotoProduto.setNomeArquivo(fotoStorageService.gerarNomeArquivo(fotoProduto.getNomeArquivo()));
		String nomeArquivoExistente = null;

		if (fotoProdutoExistente.isPresent()) {
			nomeArquivoExistente = fotoProdutoExistente.get().getNomeArquivo();
			produtoRepository.delete(fotoProdutoExistente.get());
		}
		fotoProduto = produtoRepository.save(fotoProduto);
		produtoRepository.flush();
		
		NovaFoto novaFoto = NovaFoto.builder()
				.nomeArquivo(fotoProduto.getNomeArquivo())
				.contentType(fotoProduto.getContentType())
				.inputStream(inputStream)
				.build();
		fotoStorageService.substituir(nomeArquivoExistente, novaFoto);
		return fotoProduto;
	}
	
	@Transactional
	public void remover(FotoProduto fotoProduto) {
		produtoRepository.delete(fotoProduto);
		fotoStorageService.remover(fotoProduto.getNomeArquivo());
	}

}
