package com.algaworks.algafood.domain.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.algaworks.algafood.domain.model.Cidade;

@Repository
public interface CidadeRepository {
	
	public List<Cidade> listar();
	public Cidade buscar(Long id);
	public Cidade salvar(Cidade cidade);
	public void remover(Long id);

}
