package com.algaworks.algafood.api.openapi.model;

import java.util.List;

import org.springframework.hateoas.Links;

import com.algaworks.algafood.api.model.RestauranteBasicoModel;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@ApiModel("RestaurantesBasicoModel")
@Getter
@Setter
public class RestaurantesBasicoModelOpenApi {
	
	private RestauranteBasicoEmbeddedModelOpenApi _embedded;
	private Links _links;
	
	@ApiModel("RestauranteBasicoEmbeddedModel")
	@Data
	public class RestauranteBasicoEmbeddedModelOpenApi{
		
		private List<RestauranteBasicoModel> restaurantes;
		
	}

}
