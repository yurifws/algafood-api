package com.algaworks.algafood.domain.repository;

import com.algaworks.algafood.domain.model.FotoProduto;

public interface ProdutoRepositorioQueries {

	public FotoProduto save(FotoProduto fotoProduto);
	
	public void delete(FotoProduto fotoProduto);
}
