package com.algaworks.algafood.domain.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaExceltion;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.repository.CozinhaRepository;

@Service
public class CozinhaService {
	
	@Autowired
	private CozinhaRepository cozinhaRepository;
	
	public List<Cozinha> listar(){
		return cozinhaRepository.listar();
	}
	
	public Cozinha buscar(Long id) {
		return cozinhaRepository.buscar(id);
	}
	
	public Cozinha salvar(Cozinha cozinha) {
		return cozinhaRepository.salvar(cozinha);
	}
	
	public void remover(Long id) {
		try {
			cozinhaRepository.remover(id);
			
		}catch (EmptyResultDataAccessException e) {
			throw new EntidadeNaoEncontradaExceltion(
					String.format("Não existe um cadastro de cozinha com código %d", id));
		}catch (DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(
					String.format("Cozinha de código %d náo pode ser removida, pois está em uso.", id));		
		}
	}
	
}
