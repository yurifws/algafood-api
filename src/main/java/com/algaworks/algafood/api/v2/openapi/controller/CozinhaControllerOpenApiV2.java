package com.algaworks.algafood.api.v2.openapi.controller;

import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;

import com.algaworks.algafood.api.exceptionhandler.Problem;
import com.algaworks.algafood.api.v2.model.CozinhaModelV2;
import com.algaworks.algafood.api.v2.model.input.CozinhaInputV2;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(tags = "Cozinhas")
public interface CozinhaControllerOpenApiV2 {
	
	@ApiOperation(value = "Listagem de cozinhas")
	public PagedModel<CozinhaModelV2> listar( Pageable pageable);
	
	@ApiOperation(value = "Buscar uma cozinha por Id")
	@ApiResponses(value = {
			@ApiResponse(code = 400, message = "Id da cozinha inválida", response = Problem.class),
			@ApiResponse(code = 404, message = "Cozinha não encontrada", response = Problem.class),
	})
	public CozinhaModelV2 buscar(
			@ApiParam(value = "Id de uma cozinha", example = "1", required = true) Long id);

	@ApiOperation(value = "Cadastra uma cozinha por Id")
	@ApiResponses(value = {
			@ApiResponse(code = 201, message = "Cozinha cadastrada")
	})
	public CozinhaModelV2 adicionar(
			@ApiParam(name = "corpo", value = "Representação de uma nova cozinha", required = true) CozinhaInputV2 cozinhaInput);


	@ApiOperation(value = "Atualiza uma cozinha por Id")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Cozinha atualizada"),
			@ApiResponse(code = 404, message = "Cozinha não encontrada", response = Problem.class),
	})
	public CozinhaModelV2 atualizar(
			@ApiParam(value = "Id de um cozinha", example = "1", required = true) Long id, 
			@ApiParam(name = "corpo", value = "Representação de uma cozinha com os novos dados", required = true) 
			CozinhaInputV2 cozinhaInput);

	@ApiOperation(value = "Exclui uma cozinha por Id")
	@ApiResponses(value = {
			@ApiResponse(code = 204, message = "Cozinha excluída"),
			@ApiResponse(code = 404, message = "Cozinha não encontrada", response = Problem.class),
	})
	public void remover(
			@ApiParam(value = "Id de uma cozinha", example = "1", required = true)  Long id);
	
	@ApiOperation(value = "Buscar cozinhas por nome")
	@ApiResponses(value = {
			@ApiResponse(code = 404, message = "Cozinha não encontrada", response = Problem.class)
	})
	public CollectionModel<CozinhaModelV2> consultarPorNome(
			@ApiParam(value = "Nome de uma cozinha", example = "Japonesa", required = true)  String nome);

	@ApiOperation(value = "Buscar unica cozinha por nome")
	@ApiResponses(value = {
			@ApiResponse(code = 404, message = "Cozinha não encontrada", response = Problem.class)
	})
	public ResponseEntity<CozinhaModelV2> consultarTodasPorNome(
			@ApiParam(value = "Nome de uma cozinha", example = "Japonesa", required = true)  String nome);

	@ApiOperation(value = "Verifica se existe uma cozinha por nome")
	@ApiResponses(value = {
			@ApiResponse(code = 400, message = "Id da cozinha inválida", response = Problem.class)
	})
	public boolean existsNome(
			@ApiParam(value = "Nome de uma cozinha", example = "Japonesa", required = true)  String nome);


}
