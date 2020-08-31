package com.algaworks.algafood.api.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EnderecoModel {

	@ApiModelProperty(example = "55555-120")
	private String cep;
	
	@ApiModelProperty(example = "Rua Dom Bosco")
	private String logradouro;
	
	@ApiModelProperty(example = "\"1500\"")
	private String numero;
	
	@ApiModelProperty(example = "apto 555")
	private String complemento;
	
	@ApiModelProperty(example = "Boa Vista")
	private String bairro;
	
	private CidadeResumoModel cidade;
	
}
