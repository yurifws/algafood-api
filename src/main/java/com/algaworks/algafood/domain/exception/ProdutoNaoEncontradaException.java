package com.algaworks.algafood.domain.exception;

public class ProdutoNaoEncontradaException extends EntidadeNaoEncontradaException {

	private static final long serialVersionUID = 4321357790018529363L;
	
	private static final String MSG_PRODUTO_NAO_ENCONTRADO = "Não existe um cadastro de produto com código %d para o restaurante de código %d";

	public ProdutoNaoEncontradaException(String mensagem) {
		super(mensagem);
	}

	public ProdutoNaoEncontradaException(Long restauranteId, Long produtoId) {
		this(String.format(MSG_PRODUTO_NAO_ENCONTRADO, produtoId, restauranteId));
	}
}
