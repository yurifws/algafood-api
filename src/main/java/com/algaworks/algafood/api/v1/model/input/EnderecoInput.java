package com.algaworks.algafood.api.v1.model.input;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EnderecoInput {

	@ApiModelProperty(example = "55555-120", required = true)
	@NotBlank
	private String cep;
	
	@ApiModelProperty(example = "Rua Dom Bosco", required = true)
	@NotBlank
	private String logradouro;
	
	@ApiModelProperty(example = "\"1500\"", required = true)
	@NotBlank
	private String numero;
	
	@ApiModelProperty(example = "apto 555", required = true)
	private String complemento;

	@ApiModelProperty(example = "Boa vista", required = true)
	@NotBlank
	private String bairro;

	@Valid
	@NotNull
	private CidadeIdInput cidade;
	
}
