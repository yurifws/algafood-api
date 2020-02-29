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
	public void confirmar(String codigo) {
		Pedido pedido = pedidoService.buscar(codigo);
		pedido.confirmar();
	}
	
	@Transactional
	public void entregar(String codigo) {
		Pedido pedido = pedidoService.buscar(codigo);
		pedido.entregar();
	}
	
	@Transactional
	public void cancelar(String codigo) {
		Pedido pedido = pedidoService.buscar(codigo);
		pedido.cancelar();
	}


}
