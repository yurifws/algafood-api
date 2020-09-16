package com.algaworks.algafood.api.v1.openapi.model;

import java.util.List;

import org.springframework.hateoas.Links;

import com.algaworks.algafood.api.v1.model.FormaPagamentoModel;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@ApiModel("FormasPagamentoModel")
@Getter
@Setter
public class FormasPagamentoModelOpenApi {
	
	private FormaPagamentoEmbeddedModelOpenApi _embedded;
	private Links _links;
	
	@ApiModel("FormaPagamentoEmbeddedModel")
	@Data
	public class FormaPagamentoEmbeddedModelOpenApi{
		
		private List<FormaPagamentoModel> formaPagamentos;
		
	}

}
