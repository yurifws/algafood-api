package com.algaworks.algafood.domain.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.algaworks.algafood.domain.exception.CozinhaNaoEncontradaException;
import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.repository.CozinhaRepository;

@Service
public class CozinhaService {

	private static final String MSG_COZINHA_EM_USO = "Cozinha de código %d náo pode ser removida, pois está em uso.";
	
	@Autowired
	private CozinhaRepository cozinhaRepository;

	public List<Cozinha> listar() {
		return cozinhaRepository.findAll();
	}

	public List<Cozinha> consultarTodasPorNome(String nome) {
		return cozinhaRepository.findAllByNomeContaining(nome);
	}

	public Optional<Cozinha> consultarPorNome(String nome) {
		return cozinhaRepository.findByNome(nome);
	}

	public boolean existsNome(String nome) {
		return cozinhaRepository.existsByNome(nome);
	}

	public Cozinha buscar(Long id) {
		return cozinhaRepository.findById(id)
				.orElseThrow(() -> new CozinhaNaoEncontradaException(id));
	}

	@Transactional
	public Cozinha salvar(Cozinha cozinha) {
		return cozinhaRepository.save(cozinha);
	}
	
	@Transactional
	public Cozinha atualizar(Long id, Cozinha cozinha) {
		Cozinha cozinhaAtual = buscar(id);
		BeanUtils.copyProperties(cozinha, cozinhaAtual, "id");
		return salvar(cozinhaAtual);
	}
	
	@Transactional
	public void remover(Long id) {
		buscar(id);
		try {
			cozinhaRepository.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(
					String.format(MSG_COZINHA_EM_USO, id));
		}
	}

}
