package com.algaworks.algafood.domain.exception;

public class GrupoNaoEncontradoException extends EntidadeNaoEncontradaException {
	
	private static final long serialVersionUID = 7930578165207227084L;
	
	private static final String MSG_GRUPO_NAO_ENCONTRADO = "Não existe um cadastro de grupo com código %d";

	public GrupoNaoEncontradoException(String mensagem) {
		super(mensagem);
	}

	public GrupoNaoEncontradoException(Long grupoId) {
		this(String.format(MSG_GRUPO_NAO_ENCONTRADO, grupoId));
	}
}
