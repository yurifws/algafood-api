package com.algaworks.algafood.domain.exception;

public class CozinhaNaoEncontradaException extends EntidadeNaoEncontradaException {

	private static final long serialVersionUID = 6665959216125358864L;
	
	private static final String MSG_COZINHA_NAO_ENCONTRADO = "Não existe um cadastro de cozinha com código %d";

	public CozinhaNaoEncontradaException(String mensagem) {
		super(mensagem);
	}

	public CozinhaNaoEncontradaException(Long estadoId) {
		this(String.format(MSG_COZINHA_NAO_ENCONTRADO, estadoId));
	}
}
