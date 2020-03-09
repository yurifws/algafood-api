package com.algaworks.algafood.domain.service;

import java.io.InputStream;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.algaworks.algafood.domain.model.FotoProduto;
import com.algaworks.algafood.domain.repository.ProdutoRepository;
import com.algaworks.algafood.domain.service.FotoStorageService.NovaFoto;

@Service
public class FotoProdutoService {

	@Autowired
	private ProdutoRepository produtoRepository;

	@Autowired
	private FotoStorageService fotoStorageService;

	@Transactional
	public FotoProduto salvar(FotoProduto fotoProduto, InputStream inputStream) {
		Long restauranteId = fotoProduto.getRestauranteId();
		Long produtoId = fotoProduto.getProduto().getId();
		Optional<FotoProduto> fotoProdutoExistente = produtoRepository.findFotoProdutoById(restauranteId, produtoId);
		fotoProduto.setNomeArquivo(fotoStorageService.gerarNomeArquivo(fotoProduto.getNomeArquivo()));

		if (fotoProdutoExistente.isPresent()) {
			produtoRepository.delete(fotoProdutoExistente.get());
		}
		fotoProduto = produtoRepository.save(fotoProduto);
		produtoRepository.flush();
		
		NovaFoto foto = NovaFoto.builder()
				.nomeArquivo(fotoProduto.getNomeArquivo())
				.inputStream(inputStream)
				.build();
		fotoStorageService.armazenar(foto);
		return fotoProduto;
	}

}
