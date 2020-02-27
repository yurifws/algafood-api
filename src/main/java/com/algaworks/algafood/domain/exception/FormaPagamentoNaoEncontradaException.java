package com.algaworks.algafood.domain.exception;

public class FormaPagamentoNaoEncontradaException extends EntidadeNaoEncontradaException {

	private static final long serialVersionUID = 4321357790018529363L;
	
	private static final String MSG_FORMA_PAGAMENTO_NAO_ENCONTRADO = "Não existe um cadastro de forma de pagamento com código %d";

	public FormaPagamentoNaoEncontradaException(String mensagem) {
		super(mensagem);
	}

	public FormaPagamentoNaoEncontradaException(Long formaEntregaId) {
		this(String.format(MSG_FORMA_PAGAMENTO_NAO_ENCONTRADO, formaEntregaId));
	}
}
