package com.algaworks.algafood.api.model;

import java.math.BigDecimal;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProdutoModel {
	
	@ApiModelProperty(example = "1")
	private Long id;
	
	@ApiModelProperty(example = "Espetinho de frango")
	private String nome;
	
	@ApiModelProperty(example = "Acompanha farinha e vinagrete")
	private String descricao;
	
	@ApiModelProperty(example = "13.50")
	private BigDecimal preco;
	
	@ApiModelProperty(example = "true")
	private boolean ativo;
	
}
