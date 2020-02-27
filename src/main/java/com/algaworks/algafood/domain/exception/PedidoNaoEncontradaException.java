package com.algaworks.algafood.domain.exception;

public class PedidoNaoEncontradaException extends EntidadeNaoEncontradaException {

	private static final long serialVersionUID = 929444801029522660L;
	
	private static final String MSG_PERMISSAO_NAO_ENCONTRADO = "Não existe um cadastro de pedido com código %d";

	public PedidoNaoEncontradaException(String mensagem) {
		super(mensagem);
	}

	public PedidoNaoEncontradaException(Long pedidoId) {
		this(String.format(MSG_PERMISSAO_NAO_ENCONTRADO, pedidoId));
	}
}
