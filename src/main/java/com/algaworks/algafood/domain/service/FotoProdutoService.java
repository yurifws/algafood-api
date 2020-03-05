package com.algaworks.algafood.domain.service;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.algaworks.algafood.domain.model.FotoProduto;
import com.algaworks.algafood.domain.repository.ProdutoRepository;

@Service
public class FotoProdutoService {
	
	@Autowired
	private ProdutoRepository produtoRepository; 
	
	@Transactional
	public FotoProduto salvar(FotoProduto fotoProduto) {
		Long restauranteId = fotoProduto.getRestauranteId();
		Long produtoId = fotoProduto.getProduto().getId();
		Optional<FotoProduto> fotoProdutoExistente = produtoRepository.findFotoProdutoById(restauranteId, produtoId);
		
		if(fotoProdutoExistente.isPresent()) {
			produtoRepository.delete(fotoProdutoExistente.get());
		}
		return produtoRepository.save(fotoProduto);
	}

}
