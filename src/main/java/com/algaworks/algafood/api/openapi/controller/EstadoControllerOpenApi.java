package com.algaworks.algafood.api.openapi.controller;

import org.springframework.hateoas.CollectionModel;

import com.algaworks.algafood.api.exceptionhandler.Problem;
import com.algaworks.algafood.api.model.EstadoModel;
import com.algaworks.algafood.api.model.input.EstadoInput;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(tags = "Estados")
public interface EstadoControllerOpenApi {
	
	@ApiOperation(value = "Listagem de estados")
	public CollectionModel<EstadoModel> listar();
	
	@ApiOperation(value = "Buscar um estado por Id")
	@ApiResponses(value = {
			@ApiResponse(code = 400, message = "Id do estado inválida", response = Problem.class),
			@ApiResponse(code = 404, message = "Estado não encontrado", response = Problem.class),
	})
	public EstadoModel buscar(
			@ApiParam(value = "Id de um estado", example = "1", required = true) Long id);

	@ApiOperation(value = "Cadastra um estado por Id")
	@ApiResponses(value = {
			@ApiResponse(code = 201, message = "Estado cadastrado")
	})
	public EstadoModel adicionar(
			@ApiParam(name = "corpo", value = "Representação de um novo estado", required = true) EstadoInput estadoInput);


	@ApiOperation(value = "Atualiza um estado por Id")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Estado atualizado"),
			@ApiResponse(code = 404, message = "Estado não encontrado", response = Problem.class),
	})
	public EstadoModel atualizar(
			@ApiParam(value = "Id de um estado", example = "1", required = true) Long id, 
			@ApiParam(name = "corpo", value = "Representação de um estado com os novos dados", required = true) 
			EstadoInput estadoInput);

	@ApiOperation(value = "Exclui um estado por Id")
	@ApiResponses(value = {
			@ApiResponse(code = 204, message = "Estado excluído"),
			@ApiResponse(code = 404, message = "Estado não encontrado", response = Problem.class),
	})
	public void remover(
			@ApiParam(value = "Id de um estado", example = "1", required = true)  Long id);

}
