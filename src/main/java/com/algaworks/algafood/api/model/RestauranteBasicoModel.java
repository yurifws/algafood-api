package com.algaworks.algafood.api.model;

import java.math.BigDecimal;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Relation(collectionRelation = "restaurantes")
@Getter
@Setter
public class RestauranteBasicoModel extends RepresentationModel<RestauranteBasicoModel>{
	
	@ApiModelProperty(example = "1")
	private Long id;
	
	@ApiModelProperty(example = "Coowok")
	private String nome;
	
	@ApiModelProperty(example = "12.00")
	private BigDecimal taxaFrete;
	
	private CozinhaModel cozinha;
	

}
