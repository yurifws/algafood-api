package com.algaworks.algafood.api.v2.model;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Relation(collectionRelation = "cidades")
@ApiModel(value = "Cidade", description = "Representação de uma cidade")
@Getter
@Setter
public class CidadeModelV2 extends RepresentationModel<CidadeModelV2> {
	
	@ApiModelProperty(example = "1")
	private Long idCidade;
	
	@ApiModelProperty(example = "Recife")
	private String nomeCidade;
	
	@ApiModelProperty(example = "1")
	private String idEstado;
	@ApiModelProperty(example = "Pernambuco")
	private String nomeEstado;
	
	//private EstadoModel estado;
}
