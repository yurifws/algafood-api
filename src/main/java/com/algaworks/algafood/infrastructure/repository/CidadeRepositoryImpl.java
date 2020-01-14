package com.algaworks.algafood.infrastructure.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.algaworks.algafood.domain.model.Cidade;
import com.algaworks.algafood.domain.repository.CidadeRepository;

@Component
public class CidadeRepositoryImpl implements CidadeRepository{

	@PersistenceContext
	private EntityManager entityManager;
	
	@Override
	public List<Cidade> listar(){
		return entityManager.createQuery("from Cidade", Cidade.class).getResultList();
	}
	
	@Override
	public Cidade buscar(Long id) {
		return entityManager.find(Cidade.class, id);
	}
	
	@Override
	@Transactional
	public Cidade salvar(Cidade cidade) {
		return entityManager.merge(cidade);
	}
	
	@Override
	@Transactional
	public void remover(Long id) {
		Cidade cidade = buscar(id);
		entityManager.remove(cidade);
	}

}
