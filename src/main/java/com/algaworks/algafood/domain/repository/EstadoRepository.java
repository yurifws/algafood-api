package com.algaworks.algafood.domain.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.algaworks.algafood.domain.model.Estado;

@Repository
public interface EstadoRepository {
	
	public List<Estado> listar();
	public Estado buscar(Long id);
	public Estado salvar(Estado estado);
	public void remover(Long id);

}
