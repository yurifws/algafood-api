package com.algaworks.algafood.domain.model;

import java.util.Arrays;
import java.util.List;

import lombok.Getter;

@Getter
public enum StatusPedido {
	CRIADO("Criado"),
	CONFIRMADO("Confirmado", CRIADO),
	ENTREGUE("Entregue", CONFIRMADO),
	CANCELADO("Cancelado", CRIADO);
	
	private String descricao;
	private List<StatusPedido> statusPedidosAnteriores; 
	
	StatusPedido(String descricao, StatusPedido...statusPedido ) {
		this.descricao = descricao;
		this.statusPedidosAnteriores = Arrays.asList(statusPedido);
	}
	
	public boolean naoPodeSerAlterado(StatusPedido novoStatusPedido) {
		return !novoStatusPedido.getStatusPedidosAnteriores().contains(this);
	}

}