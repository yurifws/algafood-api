package com.algaworks.algafood.domain.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.algaworks.algafood.domain.exception.ProdutoNaoEncontradaException;
import com.algaworks.algafood.domain.model.Produto;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.repository.ProdutoRepository;

@Service
public class ProdutoService {
	
	@Autowired
	private ProdutoRepository produtoRepository;
	
	public List<Produto> listar(Restaurante restaurante, boolean incluirInativos){
		if(incluirInativos) {
			return listarTodos(restaurante);
		}
		return listarAtivos(restaurante);
	}
	
	public List<Produto> listarTodos(Restaurante restaurante){
		return produtoRepository.findTodosByRestaurante(restaurante);
	}
	
	public List<Produto> listarAtivos(Restaurante restaurante){
		return produtoRepository.findAtivosByRestaurante(restaurante);
	}
	
	
	public Produto buscar(Long restauranteId, Long produtoId){
		return produtoRepository.findById(restauranteId, produtoId)
				.orElseThrow(() -> new ProdutoNaoEncontradaException(restauranteId, produtoId));
	}
	
	public Produto salvar(Produto produto) {
		return produtoRepository.save(produto);
	}
	

}
