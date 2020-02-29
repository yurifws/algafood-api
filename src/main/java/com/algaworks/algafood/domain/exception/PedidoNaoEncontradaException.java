package com.algaworks.algafood.domain.exception;

public class PedidoNaoEncontradaException extends EntidadeNaoEncontradaException {

	private static final long serialVersionUID = 929444801029522660L;
	
	private static final String MSG_PERMISSAO_NAO_ENCONTRADO = "Não existe um cadastro de pedido com código %s";


	public PedidoNaoEncontradaException(String codigo) {
		super(String.format(MSG_PERMISSAO_NAO_ENCONTRADO, codigo));
	}
}
