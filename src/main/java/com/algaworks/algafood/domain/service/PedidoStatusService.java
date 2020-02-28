package com.algaworks.algafood.domain.service;

import java.time.OffsetDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.algaworks.algafood.domain.exception.NegocioException;
import com.algaworks.algafood.domain.model.Pedido;
import com.algaworks.algafood.domain.model.StatusPedido;

@Service
public class PedidoStatusService {

	@Autowired
	private PedidoService pedidoService;
	
	@Transactional
	public void confirmar(Long id) {
		Pedido pedido = pedidoService.buscar(id);
		
		if(!pedido.getStatus().equals(StatusPedido.CRIADO)) {
			throw new NegocioException(String.format("Status do pedido %d não pode ser alterado de %s para %s", 
					id, pedido.getStatus().getDescricao(), StatusPedido.CONFIRMADO.getDescricao()));
		}
		pedido.setStatus(StatusPedido.CONFIRMADO);
		pedido.setDataConfirmacao(OffsetDateTime.now());
	}
	
	@Transactional
	public void entregar(Long id) {
		Pedido pedido = pedidoService.buscar(id);
		
		if(!pedido.getStatus().equals(StatusPedido.CONFIRMADO)) {
			throw new NegocioException(String.format("Status do pedido %d não pode ser alterado de %s para %s", 
					id, pedido.getStatus().getDescricao(), StatusPedido.ENTREGUE.getDescricao()));
		}
		pedido.setStatus(StatusPedido.ENTREGUE);
		pedido.setDataConfirmacao(OffsetDateTime.now());
	}
	
	@Transactional
	public void cancelar(Long id) {
		Pedido pedido = pedidoService.buscar(id);
		
		if(!pedido.getStatus().equals(StatusPedido.CRIADO)) {
			throw new NegocioException(String.format("Status do pedido %d não pode ser alterado de %s para %s", 
					id, pedido.getStatus().getDescricao(), StatusPedido.CANCELADO.getDescricao()));
		}
		pedido.setStatus(StatusPedido.CANCELADO);
		pedido.setDataConfirmacao(OffsetDateTime.now());
	}


}
