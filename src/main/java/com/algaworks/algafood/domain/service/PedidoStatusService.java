package com.algaworks.algafood.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.algaworks.algafood.domain.model.Pedido;

@Service
public class PedidoStatusService {

	@Autowired
	private PedidoService pedidoService;
	
	@Transactional
	public void confirmar(Long id) {
		Pedido pedido = pedidoService.buscar(id);
		pedido.confirmar();
	}
	
	@Transactional
	public void entregar(Long id) {
		Pedido pedido = pedidoService.buscar(id);
		pedido.entregar();
	}
	
	@Transactional
	public void cancelar(Long id) {
		Pedido pedido = pedidoService.buscar(id);
		pedido.cancelar();
	}


}
