package com.algaworks.algafood.domain.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.algaworks.algafood.domain.exception.CidadeNaoEncontradaException;
import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.exception.NegocioException;
import com.algaworks.algafood.domain.model.Cidade;
import com.algaworks.algafood.domain.model.Estado;
import com.algaworks.algafood.domain.repository.CidadeRepository;

@Service
public class CidadeService implements IService<Cidade>{

	private static final String MSG_CIDADE_EM_USO = "Cidade de código %d náo pode ser removida, pois está em uso.";
	
	@Autowired
	private CidadeRepository cidadeRepository;

	@Autowired
	private EstadoService estadoService;

	@Override
	public List<Cidade> listar() {
		return cidadeRepository.findAll();
	}

	@Override
	public Cidade buscar(Long id) {
		return cidadeRepository.findById(id).orElseThrow(() -> new CidadeNaoEncontradaException(id));
	}

	@Override
	@Transactional
	public Cidade salvar(Cidade cidade) {
		try {
			Long estadoId = cidade.getEstado().getId();
			Estado estado = estadoService.buscar(estadoId);
			cidade.setEstado(estado);
			return cidadeRepository.save(cidade);
		} catch (EntidadeNaoEncontradaException e) {
			throw new NegocioException(e.getMessage(), e);
		}
	}

	@Override
	@Transactional
	public void remover(Long id) {
		try {
			cidadeRepository.deleteById(id);
			cidadeRepository.flush();
		} catch (DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(String.format(MSG_CIDADE_EM_USO, id));
		}
		
	}

}
