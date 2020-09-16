package com.algaworks.algafood.api.v1.openapi.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@ApiModel("PageModel")
@Getter
@Setter
public class PageModelOpenApi {
	
	@ApiModelProperty(example = "10", value = "Quantidade de elementos por página")
	private int size;
	
	@ApiModelProperty(example = "50", value = "Total de registros")
	private int totalElements;
	
	@ApiModelProperty(example = "5", value = "Total de paginas")
	private int totalPages;
	
	@ApiModelProperty(example = "0", value = "Número da página (começao com 0)")
	private int number;

}
