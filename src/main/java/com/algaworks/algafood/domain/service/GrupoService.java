package com.algaworks.algafood.domain.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exception.GrupoNaoEncontradoException;
import com.algaworks.algafood.domain.model.Grupo;
import com.algaworks.algafood.domain.model.Permissao;
import com.algaworks.algafood.domain.repository.GrupoRepository;

@Service
public class GrupoService implements IService<Grupo>{

	private static final String MSG_GRUPO_EM_USO = "Grupo de código %d náo pode ser removido, pois está em uso.";
	
	@Autowired
	private GrupoRepository grupoRepository;

	@Autowired
	private PermissaoService permissaoService;

	@Override
	public List<Grupo> listar() {
		return grupoRepository.findAll();
	}

	@Override
	public Grupo buscar(Long id) {
		return grupoRepository.findById(id).orElseThrow(() -> new GrupoNaoEncontradoException(id));
	}

	@Override
	@Transactional
	public Grupo salvar(Grupo grupo) {
		return grupoRepository.save(grupo);
	}

	@Override
	@Transactional
	public void remover(Long id) {
		try {
			grupoRepository.deleteById(id);
			grupoRepository.flush();
		} catch (DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(String.format(MSG_GRUPO_EM_USO, id));
		}

	}
	
	@Transactional
	public void adicionarPermissao(Long grupoId, Long permissaoId) {
		Grupo grupo = buscar(grupoId);
		Permissao permissao =  permissaoService.buscar(permissaoId);
		grupo.adicionarPermissao(permissao);
	}
	
	@Transactional
	public void removerPermissao(Long grupoId, Long permissaoId) {
		Grupo grupo = buscar(grupoId);
		Permissao permissao =  permissaoService.buscar(permissaoId);
		grupo.removerPermissao(permissao);
	}

}
