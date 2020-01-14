package com.algaworks.algafood.domain.service;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.model.Estado;
import com.algaworks.algafood.domain.repository.EstadoRepository;

@Service
public class EstadoService {

	@Autowired
	private EstadoRepository estadoRepository;

	public List<Estado> listar() {
		return estadoRepository.listar();
	}

	public Estado buscar(Long id) {
		return estadoRepository.buscar(id);
	}

	public Estado salvar(Estado estado) {
		return estadoRepository.salvar(estado);
	}

	public Estado atualizar(Estado estado) {
		Estado estadoAtual = buscar(estado.getId());

		if (estadoAtual == null) {
			throw new EntidadeNaoEncontradaException(
					String.format("Não existe um cadastro de estado com código %d", estado.getId()));
		}
		BeanUtils.copyProperties(estado, estadoAtual, "id");
		return estadoRepository.salvar(estadoAtual);
	}

	public void remover(Long id) {
		try {
			estadoRepository.remover(id);
		} catch (EmptyResultDataAccessException e) {
			throw new EntidadeNaoEncontradaException(
					String.format("Não existe um cadastro de estado com código %d", id));
		} catch (DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(
					String.format("Estado de código %d náo pode ser removida, pois está em uso.", id));
		}

	}

}
