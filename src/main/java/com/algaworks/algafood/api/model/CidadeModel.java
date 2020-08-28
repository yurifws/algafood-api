package com.algaworks.algafood.api.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@ApiModel(value = "Cidade", description = "Representação de uma cidade")
@Getter
@Setter
public class CidadeModel {
	
	//@ApiModelProperty(value = "Id da cidade", example = "1")
	@ApiModelProperty(example = "1")
	private Long id;
	
	@ApiModelProperty(example = "Recife")
	private String nome;
	private EstadoModel estado;
}
