package com.algaworks.algafood.domain.exception;

public class PermissaoNaoEncontradaException extends EntidadeNaoEncontradaException {
	
	private static final long serialVersionUID = -7534563249778461893L;
	
	private static final String MSG_PERMISSAO_NAO_ENCONTRADO = "Não existe um cadastro de permissão com código %d";

	public PermissaoNaoEncontradaException(String mensagem) {
		super(mensagem);
	}

	public PermissaoNaoEncontradaException(Long estadoId) {
		this(String.format(MSG_PERMISSAO_NAO_ENCONTRADO, estadoId));
	}
}
