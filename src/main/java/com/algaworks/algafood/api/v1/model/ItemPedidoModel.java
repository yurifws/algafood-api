package com.algaworks.algafood.api.v1.model;

import java.math.BigDecimal;

import org.springframework.hateoas.RepresentationModel;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ItemPedidoModel extends RepresentationModel<ItemPedidoModel>{
	
	@ApiModelProperty(example = "1")
	private Long produtoId;
	
	@ApiModelProperty(example = "Yakissoba")
	private String produtoNome;
	
	@ApiModelProperty(example = "2")
	private int quantidade;
	
	@ApiModelProperty(example = "45.50")
	private BigDecimal precoUnitario;
	
	@ApiModelProperty(example = "91.00")
	private BigDecimal precoTotal;
	
	@ApiModelProperty(example = "Sem legumes")
	private String observacao;
	
}
