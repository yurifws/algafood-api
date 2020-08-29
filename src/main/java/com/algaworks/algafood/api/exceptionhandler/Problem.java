package com.algaworks.algafood.api.exceptionhandler;


import java.time.OffsetDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;

@ApiModel(value = Problem.MODEL_REF)
@JsonInclude(value = Include.NON_NULL)
@Getter
@Builder
public class Problem {
	
	public static final String MODEL_REF = "Problema";
	
	@ApiModelProperty(example = "400", position = 1)
	private Integer status;
	@ApiModelProperty(example = "2020-08-28T21:14:37.4990822Z", position = 5)
	private OffsetDateTime timestamp;
	@ApiModelProperty(example = "https://algafood.com.br/dados-invalidos", position = 10)
	private String type;
	@ApiModelProperty(example = "Dados inválidos", position = 15)
	private String title;
	@ApiModelProperty(example = "Um ou mais campos estão inválidos. Faça o preenchimento correto e tente novamente.", position = 20)
	private String detail;
	@ApiModelProperty(example = "Um ou mais campos estão inválidos. Faça o preenchimento correto e tente novamente.", position = 25)
	private String userMessage;
	@ApiModelProperty(value = "lista de objetos ou campos que geraram o erro (opcional)", position = 30)
	private List<Object> objects;
	
	@ApiModel(value = "ObjetoProblema")
	@Getter
	@Builder
	public static class Object{

		@ApiModelProperty(example = "estado")
		private String name;
		@ApiModelProperty(example = "O estado é obrigatório")
		private String userMessage;
	}

}
