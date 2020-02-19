package com.algaworks.algafood.domain.exception;

public class EmailUsuarioJaCadastradoException extends NegocioException {
	
	private static final long serialVersionUID = -6647074548087060193L;
	
	private static final String MSG_EMAIL_USUARIO_JA_CADASTRADO = "Já existe um usuário cadastrado com o e-mail %s";

	public EmailUsuarioJaCadastradoException(String email) {
		super(String.format(MSG_EMAIL_USUARIO_JA_CADASTRADO, email));
	}
}
