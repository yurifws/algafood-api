package com.algaworks.algafood.domain.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.algaworks.algafood.domain.exception.PedidoNaoEncontradaException;
import com.algaworks.algafood.domain.model.Pedido;
import com.algaworks.algafood.domain.repository.PedidoRepository;

@Service
public class PedidoService implements IService<Pedido>{
	
	@Autowired
	private PedidoRepository pedidoRepository;

	@Override
	public List<Pedido> listar() {
		return pedidoRepository.findAll();
	}

	@Override
	public Pedido buscar(Long id) {
		return pedidoRepository.findById(id).orElseThrow(() -> new PedidoNaoEncontradaException(id));
	}

	@Override
	public Pedido salvar(Pedido pedido) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void remover(Long id) {
		// TODO Auto-generated method stub
		
	}

}
