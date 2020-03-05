package com.algaworks.algafood.infraestructure.repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.algaworks.algafood.domain.model.FotoProduto;
import com.algaworks.algafood.domain.repository.ProdutoRepositorioQueries;

@Repository
public class ProdutoRepositoryImpl implements ProdutoRepositorioQueries{
	
	@PersistenceContext
	private EntityManager entityManager;

	@Override
	@Transactional
	public FotoProduto save(FotoProduto fotoProduto) {
		return entityManager.merge(fotoProduto);
	}

	@Override
	@Transactional
	public void delete(FotoProduto fotoProduto) {
		entityManager.remove(fotoProduto);
	}
	
}
