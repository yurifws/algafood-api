package com.algaworks.algafood.api.v1.openapi.model;

import java.util.List;

import org.springframework.hateoas.Links;

import com.algaworks.algafood.api.v1.model.UsuarioModel;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@ApiModel("UsuariosModel")
@Getter
@Setter
public class UsuariosModelOpenApi {
	
	private UsuarioEmbeddedModelOpenApi _embedded;
	private Links _links;
	
	@ApiModel("UsuarioEmbeddedModel")
	@Data
	public class UsuarioEmbeddedModelOpenApi{
		
		private List<UsuarioModel> usuarios;
		
	}

}
