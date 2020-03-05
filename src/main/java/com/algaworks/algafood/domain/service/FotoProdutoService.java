package com.algaworks.algafood.domain.service;

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
		return produtoRepository.save(fotoProduto);
	}

}
