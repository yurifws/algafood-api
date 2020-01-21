package com.algaworks.algafood.api.exceptionhandler;

import lombok.Getter;

@Getter
public enum ProblemType {
	ENTIDADE_NAO_ENCONTRADA("/entidade-nao-encontrada", "Entidade não encontrada");
	
	private String uri;
	private String title;
	
	private ProblemType(String path, String title) {
		this.uri = String.format("https://algafood.com.br%s", path);
		this.title = title;
	}
}
