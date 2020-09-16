package com.algaworks.algafood.api.v2.openapi.model;

import java.util.List;

import org.springframework.hateoas.Links;

import com.algaworks.algafood.api.v1.model.CozinhaModel;
import com.algaworks.algafood.api.v1.openapi.model.PageModelOpenApi;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@ApiModel("CozinhasModel")
@Getter
@Setter
public class CozinhasModelV2OpenApi {
	
	private CozinhaEmbeddedModelOpenApi _embedded;
	private Links _links;
	private PageModelOpenApi page;
	
	@ApiModel("CozinhaEmbeddedModel")
	@Data
	public class CozinhaEmbeddedModelOpenApi{
		
		private List<CozinhaModel> cozinhas;
		
	}
}
