package com.algaworks.algafood.domain.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exception.ProdutoNaoEncontradaException;
import com.algaworks.algafood.domain.model.Produto;
import com.algaworks.algafood.domain.repository.ProdutoRepository;

@Service
public class ProdutoService implements IService<Produto> {
	
	private static final String MSG_PRODUTO_EM_USO = "Produto de código %d náo pode ser removida, pois está em uso.";
	
	@Autowired
	private ProdutoRepository produtoRepository;
	
	@Override
	public List<Produto> listar(){
		return produtoRepository.findAll();
	}
	
	@Override
	public Produto buscar(Long id){
		return produtoRepository.findById(id).orElseThrow(() -> new ProdutoNaoEncontradaException(id));
	}
	
	@Override
	@Transactional
	public Produto salvar(Produto produto) {
		return produtoRepository.save(produto);
	}

	@Override
	@Transactional
	public void remover(Long id) {
		try {
			produtoRepository.deleteById(id);
			produtoRepository.flush();
		} catch (DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(String.format(MSG_PRODUTO_EM_USO, id));
		}

	}
	

}
