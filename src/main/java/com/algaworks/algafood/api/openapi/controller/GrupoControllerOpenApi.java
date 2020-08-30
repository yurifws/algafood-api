package com.algaworks.algafood.api.openapi.controller;

import java.util.List;

import com.algaworks.algafood.api.exceptionhandler.Problem;
import com.algaworks.algafood.api.model.GrupoModel;
import com.algaworks.algafood.api.model.input.GrupoInput;
import com.algaworks.algafood.domain.model.Grupo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(tags = "Grupos")
public interface GrupoControllerOpenApi {
	
	@ApiOperation(value = "Listagem as grupos")
	public List<GrupoModel> listar();
	
	@ApiOperation(value = "Buscar um grupo por Id")
	@ApiResponses(value = {
			@ApiResponse(code = 400, message = "Id do grupo inválida", response = Problem.class),
			@ApiResponse(code = 404, message = "Grupo não encontrado", response = Problem.class),
	})
	public GrupoModel buscar(@ApiParam(value = "Id de um grupo", example = "1") Long id);

	@ApiOperation(value = "Cadastra um grupo por Id")
	@ApiResponses(value = {
			@ApiResponse(code = 201, message = "Grupo cadastrado")
	})
	public GrupoModel adicionar(@ApiParam(name = "corpo", value = "Representação de um novo grupo") GrupoInput grupoInput);


	@ApiOperation(value = "Atualiza um grupo por Id")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Grupo atualizado"),
			@ApiResponse(code = 404, message = "Grupo não encontrado", response = Problem.class),
	})
	public Grupo atualizar(
			@ApiParam(value = "Id de um grupo", example = "1") Long id, 
			@ApiParam(name = "corpo", value = "Representação de um grupo com os novos dados") GrupoInput grupoInput);

	@ApiOperation(value = "Exclui um grupo por Id")
	@ApiResponses(value = {
			@ApiResponse(code = 204, message = "Grupo excluído"),
			@ApiResponse(code = 404, message = "Grupo não encontrado", response = Problem.class),
	})
	public void remover(
			@ApiParam(value = "Id de um grupo", example = "1")  Long id);

}
