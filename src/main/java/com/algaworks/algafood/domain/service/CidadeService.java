package com.algaworks.algafood.domain.service;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.model.Cidade;
import com.algaworks.algafood.domain.model.Estado;
import com.algaworks.algafood.domain.repository.CidadeRepository;
import com.algaworks.algafood.domain.repository.EstadoRepository;

@Service
public class CidadeService {
	
	@Autowired
	private CidadeRepository cidadeRepository;
	
	@Autowired
	private EstadoRepository estadoRepository;
	
	public List<Cidade> listar(){
		return cidadeRepository.listar();
	}
	
	public Cidade buscar(Long id){
		return cidadeRepository.buscar(id);
	}
	
	public Cidade salvar(Cidade cidade){
		Long estadoId = cidade.getEstado().getId();
		Estado estado = estadoRepository.buscar(estadoId);
		
		if(estado == null) {
			throw new EntidadeNaoEncontradaException(
					String.format("Não existe um cadastro de estado com código %d", estadoId));
		}
		cidade.setEstado(estado);
		return cidadeRepository.salvar(cidade);
	}
	
	public Cidade atualizar(Cidade cidade){
		Cidade cidadeAtual = cidadeRepository.buscar(cidade.getId());
		if(cidadeAtual == null) {
			throw new EntidadeNaoEncontradaException(
					String.format("Não existe um cadastro de cidade com código %d", cidade.getId()));
		}
		Long estadoId = cidade.getEstado().getId();
		Estado estado = estadoRepository.buscar(estadoId);
		
		if(estado == null) {
			throw new EntidadeNaoEncontradaException(
					String.format("Não existe um cadastro de estado com código %d", estadoId));
		}
		
		BeanUtils.copyProperties(cidade, cidadeAtual, "id");
		cidadeAtual.setEstado(estado);
		return cidadeRepository.salvar(cidade);
	}
	
	public void remover(Long id){
		Cidade cidade = cidadeRepository.buscar(id);
		
		if(cidade == null) {
			throw new EntidadeNaoEncontradaException(
					String.format("Não existe um cadastro de cidade com código %d", id));
		}
		cidadeRepository.remover(id);
	}
	
	

}
