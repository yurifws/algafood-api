package com.algaworks.algafood.api.v1.model.input;

import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EstadoIdInput {
	
	//@ApiModelProperty(example = "1")
	@NotNull
	private Long id;

}
