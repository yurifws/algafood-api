package com.algaworks.algafood.api.model.input;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsuarioInput {
	
	@ApiModelProperty(example = "João Henrique", required = true)
	@NotBlank
	private String nome;
	
	@ApiModelProperty(example = "j.henrique@algafood.com.br", required = true)
	@Email
	@NotBlank
	private String email;
	
	@ApiModelProperty(example = "123", required = true)
	@NotBlank
	private String senha;
	
}
