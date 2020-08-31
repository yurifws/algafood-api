package com.algaworks.algafood.api.openapi.controller;

import java.util.List;

import com.algaworks.algafood.api.exceptionhandler.Problem;
import com.algaworks.algafood.api.model.CidadeModel;
import com.algaworks.algafood.api.model.input.CidadeInput;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(tags = "Cidades")
public interface CidadeControllerOpenApi {

	@ApiOperation(value = "Listagem de cidades")
	public List<CidadeModel> listar();

	@ApiOperation(value = "Buscar uma cidade por Id")
	@ApiResponses(value = {
			@ApiResponse(code = 400, message = "Id da cidade inválida", response = Problem.class),
			@ApiResponse(code = 404, message = "Cidade não encontrada", response = Problem.class),
	})
	public CidadeModel buscar(
			@ApiParam(value = "Id de uma cidade", example = "1", required = true) Long id);

	@ApiOperation(value = "Cadastra uma cidade por Id")
	@ApiResponses(value = {
			@ApiResponse(code = 201, message = "Cidade cadastrada")
	})
	public CidadeModel adicionar(
			@ApiParam(name = "corpo", value = "Representação de uma nova cidade", required = true) CidadeInput cidadeInput);

	@ApiOperation(value = "Atualiza uma cidade por Id")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Cidade atualizada"),
			@ApiResponse(code = 404, message = "Cidade não encontrada", response = Problem.class),
	})
	public CidadeModel atualizar(
			@ApiParam(value = "Id de uma cidade", example = "1", required = true) Long id, 
			@ApiParam(name = "corpo", value = "Representação de uma cidade com os novos dados", required = true) 
			CidadeInput cidadeInput);

	@ApiOperation(value = "Exclui uma cidade por Id")
	@ApiResponses(value = {
			@ApiResponse(code = 204, message = "Cidade excluída"),
			@ApiResponse(code = 404, message = "Cidade não encontrada", response = Problem.class),
	})
	public void remover(
			@ApiParam(value = "Id de uma cidade", example = "1", required = true) Long id);

}
