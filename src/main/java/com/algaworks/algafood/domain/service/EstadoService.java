package com.algaworks.algafood.domain.service;

import java.util.List;

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

	private static final String MSG_ESTADO_EM_USO = "Estado de código %d náo pode ser removida, pois está em uso.";
	private static final String MSG_ESTADO_NAO_ENCONTRADO = "Não existe um cadastro de estado com código %d";
	@Autowired
	private EstadoRepository estadoRepository;

	public List<Estado> listar() {
		return estadoRepository.findAll();
	}

	public Estado buscar(Long id) {
		return estadoRepository.findById(id)
				.orElseThrow(() -> new EntidadeNaoEncontradaException(
						String.format(MSG_ESTADO_NAO_ENCONTRADO, id)));
	}

	public Estado salvar(Estado estado) {
		return estadoRepository.save(estado);
	}

	public void remover(Long id) {
		try {
			estadoRepository.deleteById(id);
		} catch (EmptyResultDataAccessException e) {
			throw new EntidadeNaoEncontradaException(
					String.format(MSG_ESTADO_NAO_ENCONTRADO, id));
		} catch (DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(
					String.format(MSG_ESTADO_EM_USO, id));
		}

	}

}
