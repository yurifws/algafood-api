package com.algaworks.algafood.domain.exception;

public class SenhaUsuarioNaoCoincideException extends NegocioException {

	private static final long serialVersionUID = 4560877752303668129L;
	
	private static final String MSG_SENHA_NAO_COINCIDE = "Senha atual informada não coincide com a senha do usuário.";

	public SenhaUsuarioNaoCoincideException(){
		super(MSG_SENHA_NAO_COINCIDE);
	}
}
