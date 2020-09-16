package com.algaworks.algafood.api.v2.openapi.model;

import java.util.List;

import org.springframework.hateoas.Links;

import com.algaworks.algafood.api.v1.model.CidadeModel;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@ApiModel("CidadesModel")
@Getter
@Setter
public class CidadesModelV2OpenApi {
	
	private CidadeEmbeddedModelOpenApi _embedded;
	private Links _links;
	
	@ApiModel("CidadeEmbeddedModel")
	@Data
	public class CidadeEmbeddedModelOpenApi{
		
		private List<CidadeModel> cidades;
		
	}

}
