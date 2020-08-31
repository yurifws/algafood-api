package com.algaworks.algafood.api.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsuarioModel {
	
	@ApiModelProperty(example = "1")
	private Long id;
	
	@ApiModelProperty(example = "João Henrique")
	private String nome;
	
	@ApiModelProperty(example = "j.henrique@algafood.com.br")
	private String email;
	
}
