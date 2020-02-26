package com.algaworks.algafood.domain.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.algaworks.algafood.domain.exception.PermissaoNaoEncontradaException;
import com.algaworks.algafood.domain.model.Permissao;
import com.algaworks.algafood.domain.repository.PermissaoRepository;

@Service
public class PermissaoService implements IService<Permissao>{
	
	@Autowired
	private PermissaoRepository permissaoRepository;

	@Override
	public List<Permissao> listar() {
		return permissaoRepository.findAll();
	}

	@Override
	public Permissao buscar(Long id) {
		return permissaoRepository.findById(id).orElseThrow(() -> new PermissaoNaoEncontradaException(id));
	}

	@Override
	@Transactional
	public Permissao salvar(Permissao permissao) {
		return permissaoRepository.save(permissao);
	}

	@Override
	@Transactional
	public void remover(Long id) {
		permissaoRepository.deleteById(id);
	}

}
