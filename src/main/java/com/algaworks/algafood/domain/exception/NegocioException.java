package com.algaworks.algafood.domain.exception;

public class NegocioException extends RuntimeException {

	private static final long serialVersionUID = 5107537605883591722L;

	public NegocioException(String mensagem){
		super(mensagem);
	}
	
	public NegocioException(String mensagem, Throwable cause){
		super(mensagem, cause);
	}
}
