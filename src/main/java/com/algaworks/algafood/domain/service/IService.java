package com.algaworks.algafood.domain.service;

import java.util.List;

public interface IService<Classe extends Object> {
	
	public List<Classe> listar();
	public Classe buscar(Long id);
	public Classe salvar(Classe classe);
	public void remover(Long id);

}
