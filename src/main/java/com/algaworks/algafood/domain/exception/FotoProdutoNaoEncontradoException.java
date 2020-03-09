package com.algaworks.algafood.domain.exception;

public class FotoProdutoNaoEncontradoException extends EntidadeNaoEncontradaException {
	
	private static final long serialVersionUID = -2928032818152069210L;
	
	private static final String MSG_PRODUTO_NAO_ENCONTRADO = "Não existe um cadastro de produto com código %d para o restaurante de código %d";

	public FotoProdutoNaoEncontradoException(String mensagem) {
		super(mensagem);
	}

	public FotoProdutoNaoEncontradoException(Long restauranteId, Long produtoId) {
		this(String.format(MSG_PRODUTO_NAO_ENCONTRADO, produtoId, restauranteId));
	}
}
