package com.algaworks.algafood.infraestructure.service.email;

public class EmailException extends RuntimeException{

	private static final long serialVersionUID = 7960684851071476021L;

	public EmailException(String message, Throwable cause) {
		super(message, cause);
	}

	public EmailException(String message) {
		super(message);
	}

}
