package com.algaworks.algafood.api.openapi.model;

import java.util.List;

import org.springframework.hateoas.Links;

import com.algaworks.algafood.api.model.ProdutoModel;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@ApiModel("ProdutosModel")
@Getter
@Setter
public class ProdutosModelOpenApi {
	
	private ProdutoEmbeddedModelOpenApi _embedded;
	private Links _links;
	
	@ApiModel("ProdutoEmbeddedModel")
	@Data
	public class ProdutoEmbeddedModelOpenApi{
		
		private List<ProdutoModel> produtos;
		
	}

}
