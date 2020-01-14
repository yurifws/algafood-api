package com.algaworks.algafood.domain.exception;

public class EntidadeNaoEncontradaException extends RuntimeException {

	private static final long serialVersionUID = 7850137482207891641L;

	public EntidadeNaoEncontradaException(String mensagem){
		super(mensagem);
	}
}
