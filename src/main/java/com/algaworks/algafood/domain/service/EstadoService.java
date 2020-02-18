package com.algaworks.algafood.domain.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exception.EstadoNaoEncontradoException;
import com.algaworks.algafood.domain.model.Estado;
import com.algaworks.algafood.domain.repository.EstadoRepository;

@Service
public class EstadoService implements IService<Estado>{

	private static final String MSG_ESTADO_EM_USO = "Estado de código %d náo pode ser removida, pois está em uso.";
	
	@Autowired
	private EstadoRepository estadoRepository;

	@Override
	public List<Estado> listar() {
		return estadoRepository.findAll();
	}

	@Override
	public Estado buscar(Long id) {
		return estadoRepository.findById(id).orElseThrow(() -> new EstadoNaoEncontradoException(id));
	}

	@Override
	@Transactional
	public Estado salvar(Estado estado) {
		return estadoRepository.save(estado);
	}

	@Override
	@Transactional
	public void remover(Long id) {
		try {
			estadoRepository.deleteById(id);
			estadoRepository.flush();
		} catch (DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(String.format(MSG_ESTADO_EM_USO, id));
		}

	}

}
